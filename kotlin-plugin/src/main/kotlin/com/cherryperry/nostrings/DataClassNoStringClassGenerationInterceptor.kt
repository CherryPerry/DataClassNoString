package com.cherryperry.nostrings

import org.jetbrains.kotlin.codegen.ClassBuilder
import org.jetbrains.kotlin.codegen.ClassBuilderFactory
import org.jetbrains.kotlin.codegen.ClassBuilderMode
import org.jetbrains.kotlin.codegen.extensions.ClassBuilderInterceptorExtension
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.ClassKind
import org.jetbrains.kotlin.diagnostics.DiagnosticSink
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin

class DataClassNoStringClassGenerationInterceptor(
    private val removeAll: Boolean
) : ClassBuilderInterceptorExtension {

    override fun interceptClassBuilderFactory(
        interceptedFactory: ClassBuilderFactory,
        bindingContext: BindingContext,
        diagnostics: DiagnosticSink
    ): ClassBuilderFactory =
        object : ClassBuilderFactory {

            override fun newClassBuilder(origin: JvmDeclarationOrigin): ClassBuilder {
                val classDescription = origin.descriptor as? ClassDescriptor
                return if (classDescription?.kind == ClassKind.CLASS && classDescription.isData) {
                    DataClassNoStringClassBuilder(interceptedFactory.newClassBuilder(origin), removeAll)
                } else {
                    interceptedFactory.newClassBuilder(origin)
                }
            }

            override fun getClassBuilderMode(): ClassBuilderMode {
                return interceptedFactory.classBuilderMode
            }

            override fun asText(builder: ClassBuilder?): String? {
                return interceptedFactory.asText((builder as? DataClassNoStringClassBuilder)?.classBuilder ?: builder)
            }

            override fun asBytes(builder: ClassBuilder?): ByteArray? {
                return interceptedFactory.asBytes((builder as? DataClassNoStringClassBuilder)?.classBuilder ?: builder)
            }

            override fun close() {
                interceptedFactory.close()
            }

        }

}
