package sonar.core.api.asm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**use this with {@link IEnergyHander}*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnergyHandler {

	/**specify the MODID required for the handler to load*/
	String modid();
	
	int priority();
}
