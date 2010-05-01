/*
 * Copyright 2007-2008 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package com.sun.tools.classfile;

import java.io.IOException;

import com.sun.tools.classfile.ConstantPool.*;

/**
 * See JVMS3, section 4.8.6.
 *
 *  <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 *  you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public class InnerClasses_attribute extends Attribute {
    InnerClasses_attribute(ClassReader cr, int name_index, int length) throws IOException {
        super(name_index, length);
        number_of_classes = cr.readUnsignedShort();
        classes = new Info[number_of_classes];
        for (int i = 0; i < number_of_classes; i++)
            classes[i] = new Info(cr);
    }

    public InnerClasses_attribute(ConstantPool constant_pool, Info[] classes)
            throws ConstantPoolException {
        this(constant_pool.getUTF8Index(Attribute.InnerClasses), classes);
    }

    public InnerClasses_attribute(int name_index, Info[] classes) {
        super(name_index, 2 + Info.length() * classes.length);
        this.number_of_classes = classes.length;
        this.classes = classes;
    }

    public <R, D> R accept(Visitor<R, D> visitor, D data) {
        return visitor.visitInnerClasses(this, data);
    }

    public final int number_of_classes;
    public final Info[] classes;

    public static class Info {
        Info(ClassReader cr) throws IOException {
            inner_class_info_index = cr.readUnsignedShort();
            outer_class_info_index = cr.readUnsignedShort();
            inner_name_index = cr.readUnsignedShort();
            inner_class_access_flags = new AccessFlags(cr.readUnsignedShort());
        }

        public CONSTANT_Class_info getInnerClassInfo(ConstantPool constant_pool) throws ConstantPoolException {
            if (inner_class_info_index == 0)
                return null;
            return constant_pool.getClassInfo(inner_class_info_index);
        }

        public CONSTANT_Class_info getOuterClassInfo(ConstantPool constant_pool) throws ConstantPoolException {
            if (outer_class_info_index == 0)
                return null;
            return constant_pool.getClassInfo(outer_class_info_index);
        }

        public String getInnerName(ConstantPool constant_pool) throws ConstantPoolException {
            if (inner_name_index == 0)
                return null;
            return constant_pool.getUTF8Value(inner_name_index);
        }

        public static int length() {
            return 8;
        }

        public final int inner_class_info_index;
        public final int outer_class_info_index;
        public final int inner_name_index;
        public final AccessFlags inner_class_access_flags;
    }
}
