package com.genymobile.mirror;

import com.genymobile.mirror.annotation.Class;
import com.genymobile.mirror.annotation.Constructor;
import com.genymobile.mirror.annotation.GetField;
import com.genymobile.mirror.annotation.GetInstance;
import com.genymobile.mirror.annotation.SetInstance;
import com.genymobile.mirror.exception.MirrorException;

import org.junit.Test;

public class MirrorValidatorTest {

    @Test(expected = MirrorException.class)
    public void testThatNonAnnotatedClassFails() throws Exception {
        Mirror.validateMirrorDefinition(NonClassAnnotated.class);
    }

    @Test(expected = MirrorException.class)
    public void testThatBadAnnotatedClassFails() throws Exception {
        Mirror.validateMirrorDefinition(BadClassAnnotated.class);
    }

    @Test(expected = MirrorException.class)
    public void testThatBadConstructorFails() throws Exception {
        Mirror.validateMirrorDefinition(BadConstructor.class);
    }

    @Test
    public void testThatGoodConstructorWorks() throws Exception {
        Mirror.validateMirrorDefinition(GoodConstructor.class);
    }

    @Test(expected = MirrorException.class)
    public void testThatMethodWithTooMuchAnnotationsFails() throws Exception {
        Mirror.validateMirrorDefinition(TooMuchAnnotations.class);
    }

    @Test(expected = MirrorException.class)
    public void testThatWrongFieldFails() throws Exception {
        Mirror.validateMirrorDefinition(WrongField.class);
    }

    @Test(expected = MirrorException.class)
    public void testThatWrongFieldTypeFails() throws Exception {
        Mirror.validateMirrorDefinition(WrongFieldType.class);
    }

    @Test
    public void testThatGoodFieldWorks() throws Exception {
        Mirror.validateMirrorDefinition(GoodField.class);
    }

    @Test(expected = MirrorException.class)
    public void testThatWrongMethodFails() throws Exception {
        Mirror.validateMirrorDefinition(WrongMethod.class);
    }

    @Test
    public void testThatGoodMethodWorks() throws Exception {
        Mirror.validateMirrorDefinition(GoodMethod.class);
    }

    @Test(expected = MirrorException.class)
    public void testThatWrongGetInstanceMethodFails() throws Exception {
        Mirror.validateMirrorDefinition(WrongGetInstance.class);
    }

    @Test
    public void testThatGoodGetInstanceWorks() throws Exception {
        Mirror.validateMirrorDefinition(GoodGetInstance.class);
    }

    @Test(expected = MirrorException.class)
    public void testThatMissingSetInstanceWhenReturningWrappedClassFails() throws Exception {
        Mirror.validateMirrorDefinition(MissingSetInstanceWhenReturningAnWrapped.class);
    }

    @Test
    public void testThatCorrectSetInstanceWhenReturningWrappedClassWorks() throws Exception {
        Mirror.validateMirrorDefinition(SetInstanceWhenReturningAnWrapped.class);
    }

    @Test
    public void testThatGoodMirrorWorks() throws Exception {
        Mirror.validateMirrorDefinition(GoodMirror.class);
    }
}

interface NonClassAnnotated {
}

@Class("com.unknown.MyClass")
interface BadClassAnnotated {
}

@Class("com.genymobile.mirror.target.PrivateDummyClass")
interface BadConstructor {
    @Constructor
    Object construct(int insteadOfString);

}

@Class("com.genymobile.mirror.target.PrivateDummyClass")
interface GoodConstructor {
    @Constructor
    Object construct(String string);

}

@Class("com.genymobile.mirror.target.PublicDummyClass")
interface TooMuchAnnotations {
    @Constructor
    @GetField("field")
    Object getField();

}

@Class("com.genymobile.mirror.target.PublicDummyClass")
interface WrongField {
    @GetField("wrongfield")
    Object getField();

}

@Class("com.genymobile.mirror.target.PublicDummyClass")
interface WrongFieldType {
    @GetField("field")
    int getField();

}

@Class("com.genymobile.mirror.target.PublicDummyClass")
interface GoodField {
    @GetField("field")
    Object getField();

}

@Class("com.genymobile.mirror.target.PublicDummyClass")
interface WrongMethod {
    String getString(String shouldBeAnInt);

}

@Class("com.genymobile.mirror.target.PublicDummyClass")
interface GoodMethod {
    String getString(int anInt);

}

@Class("com.genymobile.mirror.target.PublicDummyClass")
interface GoodMirror {
    @GetField("field")
    String getField();

    @GetField("array")
    String[] getArrayField();

}

@Class("com.genymobile.mirror.target.PublicDummyClass")
interface WrongGetInstance {
    @GetInstance
    void getInstance(String in);
}

@Class("com.genymobile.mirror.target.PublicDummyClass")
interface GoodGetInstance {
    @GetInstance
    Object getInstance();
}

@Class("com.genymobile.mirror.target.PrivateDummyClass")
interface MissingSetInstanceWhenReturningAnWrapped {

    MissingSetInstanceWhenReturningAnWrapped getPrivateDummyInstance();
}

@Class("com.genymobile.mirror.target.PrivateDummyClass")
interface SetInstanceWhenReturningAnWrapped {

    @SetInstance
    void setInstance(Object object);

    SetInstanceWhenReturningAnWrapped getPrivateDummyInstance();
}
