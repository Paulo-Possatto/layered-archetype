package ${package}.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "local")
class LayeredArchitectureTest {

  private static JavaClasses classes;

  @BeforeAll
  static void setup() {
    classes = new ClassFileImporter()
        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        .importPackages("${package}");
  }

  //TODO: Create architecture tests. See more at https://www.archunit.org/
}
