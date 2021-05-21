package archTest;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages={"test","session","draw"})
public class MyArchTest {

	@ArchTest
	public final static ArchRule r_package=classes().that().resideInAPackage("session")
	.should().onlyBeAccessed().byClassesThat().resideInAnyPackage("test","session");
	
	@ArchTest
	public final static ArchRule r_finalField=classes().that().resideInAPackage("draw")
	.should().bePublic();
	
	@ArchTest
	public final static ArchRule r_endWith=classes().that().haveNameMatching(".*Test")
	.should().resideInAPackage("test");
	
	@ArchTest
	public final static ArchRule r_4=classes().that().areInterfaces()
	.should().bePrivate();
	
}

