package com.cherryperry.nostrings

import org.jetbrains.kotlin.codegen.ClassBuilder
import org.jetbrains.kotlin.codegen.DelegatingClassBuilder
import org.jetbrains.kotlin.codegen.TransformationMethodVisitor
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin
import org.jetbrains.org.objectweb.asm.Label
import org.jetbrains.org.objectweb.asm.MethodVisitor
import org.jetbrains.org.objectweb.asm.Opcodes
import org.jetbrains.org.objectweb.asm.tree.InsnNode
import org.jetbrains.org.objectweb.asm.tree.LdcInsnNode
import org.jetbrains.org.objectweb.asm.tree.MethodNode

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
        val original = super.newMethod(origin, access, name, desc, signature, exceptions)
        return when (name) {
            "toString" ->
                MethodTransformer(
                    delegate = original,
                    access = access,
                    name = name,
                    desc = desc,
                    signature = signature,
                    exceptions = exceptions,
                    api = Opcodes.ASM5,
                    transformation = ::generateSuperString
                )
            "hashCode" ->
                if (removeAll) {
                    MethodTransformer(
                        delegate = original,
                        access = access,
                        name = name,
                        desc = desc,
                        signature = signature,
                        exceptions = exceptions,
                        api = Opcodes.ASM5,
                        transformation = ::generateSuperHashCode
                    )
                } else {
                    original
                }
            "equals" ->
                if (removeAll) {
                    MethodTransformer(
                        delegate = original,
                        access = access,
                        name = name,
                        desc = desc,
                        signature = signature,
                        exceptions = exceptions,
                        api = Opcodes.ASM5,
                        transformation = ::generateSuperEquals
                    )
                } else {
                    original
                }
            else ->
                original
        }
    }

    private fun generateToString(methodNode: MethodNode, transformer: MethodVisitor) {
        /*
        Bytecode:

          public toString()Ljava/lang/String;
          @Lorg/jetbrains/annotations/NotNull;() // invisible
           L0
            LINENUMBER 15 L0
            LDC ""
            ARETURN
           L1
            LOCALVARIABLE this Ldev/afanasev/sekret/sample/Admin; L0 L1 0
            MAXSTACK = 1
            MAXLOCALS = 1

         Remove one local variable, we do not need it.

         */
        methodNode.instructions.add(LdcInsnNode(""))
        methodNode.instructions.add(InsnNode(Opcodes.ARETURN))
        methodNode.maxStack = 1
        methodNode.maxLocals = 0
    }

    private fun generateEquals(methodNode: MethodNode) {
        /*
        Bytecode:

          public equals(Ljava/lang/Object;)Z
            // annotable parameter count: 1 (visible)
            // annotable parameter count: 1 (invisible)
            @Lorg/jetbrains/annotations/Nullable;() // invisible, parameter 0
           L0
            LINENUMBER 5 L0
            ICONST_0
            IRETURN
           L1
            LOCALVARIABLE this Lcom/cherryperry/nostrings/Sample; L0 L1 0
            LOCALVARIABLE other Ljava/lang/Object; L0 L1 1
            MAXSTACK = 1
            MAXLOCALS = 2

         Remove one local variable, we do not need it.

         */
        methodNode.instructions.add(InsnNode(Opcodes.ICONST_0))
        methodNode.instructions.add(InsnNode(Opcodes.IRETURN))
        methodNode.maxStack = 1
        methodNode.maxLocals = 0
    }

    private fun generateHashCode(methodNode: MethodNode) {
        /*
        Bytecode:

          public hashCode()I
           L0
            LINENUMBER 5 L0
            ICONST_0
            IRETURN
           L1
            LOCALVARIABLE this Lcom/cherryperry/nostrings/Sample; L0 L1 0
            MAXSTACK = 1
            MAXLOCALS = 1

         Remove both local variables, we do not need it.

         */
        methodNode.instructions.add(InsnNode(Opcodes.ICONST_0))
        methodNode.instructions.add(InsnNode(Opcodes.IRETURN))
        methodNode.maxStack = 1
        methodNode.maxLocals = 0
    }

    private fun generateSuperString(methodNode: MethodNode) {
        /*
        Bytecode:

          public toString()Ljava/lang/String;
          @Lorg/jetbrains/annotations/NotNull;() // invisible
           L0
            LINENUMBER 15 L0
            ALOAD 0
            INVOKESPECIAL java/lang/Object.toString ()Ljava/lang/String;
            ARETURN
           L1
            LOCALVARIABLE this Lcom/cherryperry/nostrings/Sample; L0 L1 0
            MAXSTACK = 1
            MAXLOCALS = 1

         */

        val label0 = Label()
        methodNode.visitLabel(label0)
        methodNode.visitVarInsn(Opcodes.ALOAD, 0)
        methodNode.visitMethodInsn(
            Opcodes.INVOKESPECIAL,
            "java/lang/Object",
            methodNode.name,
            methodNode.desc,
            false
        )
        methodNode.visitInsn(Opcodes.ARETURN)
        val label1 = Label()
        methodNode.visitLabel(label1)
        methodNode.visitLocalVariable(
            "this",
            "L${classBuilder.thisName};",
            null,
            label0,
            label1,
            0
        )
        methodNode.visitMaxs(1, 1)
    }

    private fun generateSuperHashCode(methodNode: MethodNode) {
        /*
        Bytecode:

          public hashCode()I
           L0
            LINENUMBER 9 L0
            ALOAD 0
            INVOKESPECIAL java/lang/Object.hashCode ()I
            IRETURN
           L1
            LOCALVARIABLE this Lcom/cherryperry/nostrings/Sample; L0 L1 0
            MAXSTACK = 1
            MAXLOCALS = 1

         */

        val label0 = Label()
        methodNode.visitLabel(label0)
        methodNode.visitVarInsn(Opcodes.ALOAD, 0)
        methodNode.visitMethodInsn(
            Opcodes.INVOKESPECIAL,
            "java/lang/Object",
            methodNode.name,
            methodNode.desc,
            false
        )
        methodNode.visitInsn(Opcodes.IRETURN)
        val label1 = Label()
        methodNode.visitLabel(label1)
        methodNode.visitLocalVariable(
            "this",
            "L${classBuilder.thisName};",
            null,
            label0,
            label1,
            0
        )
        methodNode.visitMaxs(1, 1)
    }

    private fun generateSuperEquals(methodNode: MethodNode) {
        /*
        Bytecode:

          public equals(Ljava/lang/Object;)Z
            // annotable parameter count: 1 (visible)
            // annotable parameter count: 1 (invisible)
            @Lorg/jetbrains/annotations/Nullable;() // invisible, parameter 0
           L0
            LINENUMBER 5 L0
            ALOAD 0
            ALOAD 1
            INVOKESPECIAL java/lang/Object.equals (Ljava/lang/Object;)Z
            IRETURN
           L1
            LOCALVARIABLE this Lcom/cherryperry/nostrings/Sample; L0 L1 0
            LOCALVARIABLE other Ljava/lang/Object; L0 L1 1
            MAXSTACK = 2
            MAXLOCALS = 2

         */

        val label0 = Label()
        methodNode.visitLabel(label0)
        methodNode.visitVarInsn(Opcodes.ALOAD, 0)
        methodNode.visitVarInsn(Opcodes.ALOAD, 1)
        Object().hashCode()
        methodNode.visitMethodInsn(
            Opcodes.INVOKESPECIAL,
            "java/lang/Object",
            methodNode.name,
            methodNode.desc,
            false
        )
        methodNode.visitInsn(Opcodes.IRETURN)
        val label1 = Label()
        methodNode.visitLabel(label1)
        methodNode.visitLocalVariable(
            "this",
            "L${classBuilder.thisName};",
            null,
            label0,
            label1,
            0
        )
        methodNode.visitLocalVariable(
            "other",
            "Ljava/lang/Object;",
            null,
            label0,
            label1,
            0
        )
        methodNode.visitMaxs(2, 2)
    }

    private class MethodTransformer(
        delegate: MethodVisitor,
        access: Int,
        name: String,
        desc: String,
        signature: String?,
        exceptions: Array<out String>?,
        api: Int,
        private val transformation: (methodNode: MethodNode) -> Unit
    ) : TransformationMethodVisitor(delegate, access, name, desc, signature, exceptions, api) {

        override fun performTransformations(methodNode: MethodNode) {
            methodNode.instructions.clear()
            transformation(methodNode)
            methodNode.check(Opcodes.ASM5)
        }

    }

}
