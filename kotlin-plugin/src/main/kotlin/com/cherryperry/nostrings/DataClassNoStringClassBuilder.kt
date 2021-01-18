package com.cherryperry.nostrings

import org.jetbrains.kotlin.codegen.ClassBuilder
import org.jetbrains.kotlin.codegen.DelegatingClassBuilder
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin
import org.jetbrains.org.objectweb.asm.MethodVisitor
import org.jetbrains.org.objectweb.asm.Opcodes

class DataClassNoStringClassBuilder(
    val classBuilder: ClassBuilder,
    private val removeAll: Boolean
) : DelegatingClassBuilder() {

    override fun getDelegate(): ClassBuilder = classBuilder

    override fun newMethod(
        origin: JvmDeclarationOrigin,
        access: Int,
        name: String,
        desc: String,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val callSuper: () -> MethodVisitor = { super.newMethod(origin, access, name, desc, signature, exceptions) }
        return when (name) {
            "toString" -> EmptyVisitor
            "hashCode" -> if (removeAll) EmptyVisitor else callSuper()
            "equals" -> if (removeAll) EmptyVisitor else callSuper()
            else -> callSuper()
        }
    }

    private object EmptyVisitor : MethodVisitor(Opcodes.ASM5)

}
