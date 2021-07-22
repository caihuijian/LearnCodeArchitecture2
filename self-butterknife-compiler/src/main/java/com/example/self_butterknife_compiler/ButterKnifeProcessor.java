package com.example.self_butterknife_compiler;


import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import com.example.self_butterknife_annotations.BindView;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class ButterKnifeProcessor extends AbstractProcessor {
    private Filer mFiler;
    private Elements mElementUtils;

    // 初始化
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
    }

    /**
     * 用来指定支持的 SourceVersion
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    // 有注解就都会进process方法 这里是生成代码的核心
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        System.out.println("===process===");
//        System.out.println("annotations "+annotations);
//        System.out.println("roundEnv "+roundEnv);

        // 获取所有拥有注释BindView的变量
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BindView.class);
        // 该map 存储的key是Activity的完整路径 value是其中被BindView标记的所有变量的list
        // 例如 {com.example.client.MainActivity=[mTextView,mtv1]}
        Map<Element, List<Element>> elementsMap = new LinkedHashMap<>();
        for (Element element : elements) {
            // 获取拥有注释BindView的变量位于哪个类 例如 com.example.client.MainActivity
            Element enclosingElement = element.getEnclosingElement();
            // 查看map中没有存储过该类
            List<Element> viewBindElements = elementsMap.get(enclosingElement);
            // 如果map中没有存储过该类 创建一个新的list 将Activity作为key list作为value 存储到map
            if (viewBindElements == null) {
                viewBindElements = new ArrayList<>();
                elementsMap.put(enclosingElement, viewBindElements);
            }
            // 将拥有注释BindView的变量存储到list
            viewBindElements.add(element);
        }

        // 遍历添加相关注解的Class 准备生成代码
        for (Map.Entry<Element, List<Element>> entry : elementsMap.entrySet()) {
            // 类的完整报名+类名
            Element enclosingElement = entry.getKey();
            // 类中存储的拥有注释BindView的变量集合
            List<Element> viewBindElements = entry.getValue();

            // 从完整包名+类名获取简短类名 例如 从 com.example.client.MainActivity 得到 MainActivity
            String activityClassNameStr = enclosingElement.getSimpleName().toString();

            ClassName activityClassName = ClassName.bestGuess(activityClassNameStr);
            // 用包名+类名创建接口的className
            // 目前是hardcode的包名
            ClassName unbinderClassName = ClassName.get("com.example.self_butterknife", "Unbinder");

            // 创建的类名为 类名+_ViewBinding
            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(activityClassNameStr + "_ViewBinding")
                    // 该类是public final的
                    .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                    // 实现了一个接口
                    .addSuperinterface(unbinderClassName)
                    // 创建一个activityClassName的实例 私有对象 变量名为target
                    .addField(activityClassName, "target", Modifier.PRIVATE);


            /*****unbind 方法实现 start *****/
            // 利用包名+类名 构建 android.support.annotation.CallSuper的ClassName
            ClassName callSuperClassName = ClassName.get("androidx.annotation", "CallSuper");
            // 创建unbind方法
            MethodSpec.Builder unbindMethodBuilder = MethodSpec.methodBuilder("unbind")
                    // 给unbind方法加上Override注解
                    .addAnnotation(Override.class)
                    // 给unbind方法加上CallSuper注解
                    .addAnnotation(callSuperClassName)
                    // unbind方法是public final的
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
            // 方法内部添加 一行内容
            unbindMethodBuilder.addStatement("$T target = this.target", activityClassName);
            unbindMethodBuilder.addStatement("if (target == null) throw new IllegalStateException(\"Bindings already cleared. target is null! \");");
            /*****unbind方法实现 end *****/


            // 创建构造函数
            MethodSpec.Builder constructorMethodBuilder = MethodSpec.constructorBuilder()
                    // 给构造方法添加一个参数类型为activityClassName 值为target的参数
                    .addParameter(activityClassName, "target")
                    // 构造方法添加一行代码 this.target = target;
                    .addStatement("this.target = target");
            // 遍历添加相关注解的变量
            for (Element viewBindElement : viewBindElements) {
                // 获得变量名称
                String filedName = viewBindElement.getSimpleName().toString();
                // 获取Utils的ClassName
                ClassName utilsClassName = ClassName.get("com.example.self_butterknife", "Utils");
                // 获取注解的值 即view的id
                int resId = viewBindElement.getAnnotation(BindView.class).value();
                // TODO 根据id的int值反推其资源id

                // 在构造方法生成 类似 target.textView1 = Utils.findViewById(target, R.id.tv1); 的代码
                constructorMethodBuilder.addStatement("target.$L = $T.findViewById(target, $L)", filedName, utilsClassName, resId);
                // 在unbind方法生成类似 target.textView1 = null;的语句
                unbindMethodBuilder.addStatement("target.$L = null", filedName);
            }


            // 加入两个方法
            classBuilder.addMethod(unbindMethodBuilder.build());
            classBuilder.addMethod(constructorMethodBuilder.build());

            // 生成类，看下效果
            try {
                // 根据完整包名+类名得到包名
                String packageName = mElementUtils.getPackageOf(enclosingElement).getQualifiedName().toString();
                // 文件创建的位置在packageName下
                JavaFile.builder(packageName, classBuilder.build())
                        // Java文件 头部添加注释添加
                        .addFileComment("由ButterKnifeProcessor自动生成 请勿修改")
                        .build()
                        // 写文件
                        .writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("发生异常...");
            }
        }
        return false;
    }


    // 哪些注解支持自动生成代码
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    // 支持自动生成文件的Annotation(需要解析的自定义注解 例如 BindView OnClick)
    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        // 需要解析的自定义注解 BindView  OnClick
        annotations.add(BindView.class);
        return annotations;
    }

}