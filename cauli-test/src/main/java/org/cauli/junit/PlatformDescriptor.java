package org.cauli.junit;

import org.databene.benerator.DefaultPlatformDescriptor;

/**
 */
public class PlatformDescriptor extends DefaultPlatformDescriptor{
    private static boolean formattedByDefault = false;
    public static boolean isFormattedByDefault() {
        return formattedByDefault;
    }
    public static void setFormattedByDefault(boolean formattedByDefault) {
        PlatformDescriptor.formattedByDefault = formattedByDefault;
    }
    public PlatformDescriptor() {
        super(PlatformDescriptor.class.getName());
    }
}
