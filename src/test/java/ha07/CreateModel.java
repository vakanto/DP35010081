package ha07;

import org.junit.Test;
import org.fulib.Fulib;
import org.fulib.builder.ClassBuilder;
import org.fulib.builder.ClassModelBuilder;
import org.fulib.classmodel.ClassModel;
import org.junit.Test;
public class CreateModel {

    @Test
    public void createModel(){
        ClassModelBuilder mb = Fulib.classModelBuilder("ha07.Shop.Model", "src/main/java");
        ClassBuilder warehouseClass = mb.buildClass("Warehouse")
                .buildAttribute("name", mb.STRING);

        ClassBuilder warehouseOrderClass = mb.buildClass("WarehouseOrder")
                .buildAttribute("name", mb.STRING);


        warehouseClass.buildAssociation(warehouseClass, "warehouse", mb.ONE, "orders", mb.MANY);

        ClassModel classModel = mb.getClassModel();
        Fulib.generator().generate(classModel);

    }
}
