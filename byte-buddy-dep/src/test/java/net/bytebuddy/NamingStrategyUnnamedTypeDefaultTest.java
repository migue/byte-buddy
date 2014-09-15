package net.bytebuddy;

import net.bytebuddy.instrumentation.type.TypeDescription;
import net.bytebuddy.modifier.SyntheticState;
import net.bytebuddy.modifier.TypeManifestation;
import net.bytebuddy.modifier.Visibility;
import net.bytebuddy.utility.HashCodeEqualsTester;
import net.bytebuddy.utility.MockitoRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.asm.Opcodes;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NamingStrategyUnnamedTypeDefaultTest {

    private static final int MODIFIERS = 42;

    @Rule
    public TestRule mockitoRule = new MockitoRule(this);

    @Mock
    private TypeDescription superType, interfaceType;

    @Mock
    private ClassFileVersion classFileVersion, other;

    private List<TypeDescription> interfaceTypes;

    @Before
    public void setUp() throws Exception {
        interfaceTypes = Arrays.asList(interfaceType);
    }

    @Test
    public void testProperties() throws Exception {
        NamingStrategy.UnnamedType unnamedType = new NamingStrategy.UnnamedType.Default(superType, interfaceTypes, MODIFIERS, classFileVersion);
        assertThat(unnamedType.getDeclaredInterfaces(), is((Collection<TypeDescription>) interfaceTypes));
        assertThat(unnamedType.getClassFileVersion(), is(classFileVersion));
        assertThat(unnamedType.getSuperClass(), is(superType));
    }

    @Test
    public void testVisibilityProperty() throws Exception {
        assertThat(new NamingStrategy.UnnamedType.Default(superType, interfaceTypes, Opcodes.ACC_PRIVATE, classFileVersion).getVisibility(),
                is(Visibility.PRIVATE));
        assertThat(new NamingStrategy.UnnamedType.Default(superType, interfaceTypes, Opcodes.ACC_PROTECTED, classFileVersion).getVisibility(),
                is(Visibility.PROTECTED));
        assertThat(new NamingStrategy.UnnamedType.Default(superType, interfaceTypes, Opcodes.ACC_PUBLIC, classFileVersion).getVisibility(),
                is(Visibility.PUBLIC));
        assertThat(new NamingStrategy.UnnamedType.Default(superType, interfaceTypes, 0, classFileVersion).getVisibility(),
                is(Visibility.PACKAGE_PRIVATE));
    }

    @Test
    public void testSyntheticProperty() throws Exception {
        assertThat(new NamingStrategy.UnnamedType.Default(superType, interfaceTypes, Opcodes.ACC_SYNTHETIC, classFileVersion).getSyntheticState(),
                is(SyntheticState.SYNTHETIC));
        assertThat(new NamingStrategy.UnnamedType.Default(superType, interfaceTypes, 0, classFileVersion).getSyntheticState(),
                is(SyntheticState.NON_SYNTHETIC));
    }

    @Test
    public void testTypeManifestationProperty() throws Exception {
        assertThat(new NamingStrategy.UnnamedType.Default(superType, interfaceTypes, Opcodes.ACC_ABSTRACT, classFileVersion).getTypeManifestation(),
                is(TypeManifestation.ABSTRACT));
        assertThat(new NamingStrategy.UnnamedType.Default(superType, interfaceTypes, Opcodes.ACC_ABSTRACT | Opcodes.ACC_ENUM, classFileVersion).getTypeManifestation(),
                is(TypeManifestation.ABSTRACT_ENUM));
        assertThat(new NamingStrategy.UnnamedType.Default(superType, interfaceTypes, Opcodes.ACC_ENUM | Opcodes.ACC_ENUM, classFileVersion).getTypeManifestation(),
                is(TypeManifestation.ENUM));
        assertThat(new NamingStrategy.UnnamedType.Default(superType, interfaceTypes, Opcodes.ACC_FINAL, classFileVersion).getTypeManifestation(),
                is(TypeManifestation.FINAL));
        assertThat(new NamingStrategy.UnnamedType.Default(superType, interfaceTypes, Opcodes.ACC_INTERFACE, classFileVersion).getTypeManifestation(),
                is(TypeManifestation.INTERFACE));
        assertThat(new NamingStrategy.UnnamedType.Default(superType, interfaceTypes, 0, classFileVersion).getTypeManifestation(),
                is(TypeManifestation.PLAIN));
    }

    @Test
    public void testHashCodeEquals() throws Exception {
        HashCodeEqualsTester.of(NamingStrategy.UnnamedType.Default.class).apply();
    }
}
