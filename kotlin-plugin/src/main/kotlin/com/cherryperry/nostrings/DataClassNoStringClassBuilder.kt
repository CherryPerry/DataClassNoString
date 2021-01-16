package com.cherryperry.nostrings

import org.jetbrains.kotlin.codegen.ClassBuilder
import org.jetbrains.kotlin.codegen.DelegatingClassBuilder
import org.jetbrains.kotlin.codegen.TransformationMethodVisitor
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin
import org.jetbrains.org.objectweb.asm.Label
import org.jetbrains.org.objectweb.asm.MethodVisitor
import org.jetbrains.org.objectweb.asm.Opcodes
import org.jetbrains.org.objectweb.asm.tree.InsnNode
import org.jetbrains.org.objectweb.asm.tree.IntInsnNode
import org.jetbrains.org.objectweb.asm.tree.LabelNode
import org.jetbrains.org.objectweb.asm.tree.LdcInsnNode
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode
import org.jetbrains.org.objectweb.asm.tree.MethodNode

class DataClassNoStringClassBuilder(
    val classBuilder: ClassBuilder
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
        if (name == "toString") {
            return object : TransformationMethodVisitor(
                delegate = original,
                access = access,
                name = name,
                desc = desc,
                signature = signature,
                exceptions = exceptions,
                api = Opcodes.ASM5
            ) {
                override fun performTransformations(methodNode: MethodNode) {
                    methodNode.instructions.clear()
                    generateReturnEmptyString(methodNode)
                    methodNode.check(Opcodes.ASM5)
                }
            }
        } else {
            return original
        }
    }

    private fun generateReturnEmptyString(methodNode: MethodNode) {
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

    private fun generateReturnSuper(methodNode: MethodNode) {
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
            LOCALVARIABLE this Ldev/afanasev/sekret/sample/Admin; L0 L1 0
            MAXSTACK = 1
            MAXLOCALS = 1

         */

        val label0 = LabelNode(Label())
        val label1 = LabelNode(Label())
        methodNode.instructions.add(label0)
        methodNode.instructions.add(IntInsnNode(Opcodes.ALOAD, 0))
        methodNode.instructions.add(
            MethodInsnNode(
                Opcodes.INVOKESPECIAL,
                "java/lang/Object",
                "toString",
                "()Ljava/lang/String;",
                false
            )
        )
        methodNode.instructions.add(InsnNode(Opcodes.ARETURN))
        methodNode.instructions.add(label1)

        methodNode.maxStack = 1
        methodNode.maxLocals = 0
        methodNode.localVariables.add(
            LocalVariableNode(
                "this",
                "L",
                "dev/afanasev/sekret/sample/Admin",
                label0,
                label1,
                0
            )
        )
    }

}
