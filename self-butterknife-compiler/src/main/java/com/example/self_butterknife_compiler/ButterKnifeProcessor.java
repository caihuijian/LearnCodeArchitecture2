package com.example.self_butterknife_compiler;

import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@AutoService(Process.class)
public class ButterKnifeProcessor extends AbstractProcessor {

//    /**
//     * 用来指定支持的 SourceVersion
//     *
//     * @return
//     */
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        super.getSupportedSourceVersion();
//        return SourceVersion.latestSupported();
//    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // System.out.println("xxxxxxxxxxxxxxxxxxxxd05>");
        return false;
    }
}