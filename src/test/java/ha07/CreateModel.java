package ha07;

import org.junit.Test;
import org.fulib.Fulib;
import org.fulib.builder.ClassBuilder;
import org.fulib.builder.ClassModelBuilder;
import org.fulib.classmodel.ClassModel;
import org.junit.Test;
public class CreateModel {

    @Test
    public void createWarehouseModel(){
        ClassModelBuilder mb = Fulib.classModelBuilder("ha07.Warehouse.Model", "src/main/java");

        ClassBuilder warehouseClass = mb.buildClass("Warehouse")
                .buildAttribute("name", mb.STRING);

        ClassBuilder warehouseOrderClass = mb.buildClass("WarehouseOrder")
                .buildAttribute("address", mb.STRING)
                .buildAttribute("id", mb.STRING)
                .buildAttribute("product", mb.STRING);

        ClassBuilder warehouseProductClass = mb.buildClass("WarehouseProduce")
                .buildAttribute("name", mb.STRING)
                .buildAttribute("id", mb.STRING);

        ClassBuilder lotClass = mb.buildClass("Lot")
                .buildAttribute("id", mb.STRING)
                .buildAttribute("lotSize", mb.DOUBLE);


        ClassBuilder palettePlaceClass = mb.buildClass("PalettePlace")
                .buildAttribute("column", mb.DOUBLE)
                .buildAttribute("id", mb.STRING)
                .buildAttribute("row", mb.DOUBLE);



        warehouseClass.buildAssociation(warehouseClass, "orders", mb.MANY, "warehouse", mb.ONE);
        warehouseClass.buildAssociation(warehouseProductClass, "products", mb.MANY, "warehouse", mb.ONE);
        warehouseClass.buildAssociation(palettePlaceClass, "places", mb.MANY, "warehouse", mb.ONE);

        warehouseOrderClass.buildAssociation(warehouseProductClass, "products", mb.MANY, "orders", mb.MANY);

        warehouseProductClass.buildAssociation(lotClass, "lots", mb.MANY, "wareHouseProduct",mb.ONE);

        lotClass.buildAssociation(palettePlaceClass, "lot", mb.ONE, "paletteplace", mb.ONE);


        ClassModel classModel = mb.getClassModel();
        Fulib.generator().generate(classModel);
    }

    @Test
    public void createShopModel(){
        ClassModelBuilder mb = Fulib.classModelBuilder("ha07.Warehouse.Model", "src/main/java");






        ClassModel classModel = mb.getClassModel();
        Fulib.generator().generate(classModel);
    }
}
