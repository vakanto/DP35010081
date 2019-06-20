package ha07_ha08;

import ha07_ha08.Warehouse.Model.WarehouseProduct;
import org.junit.Test;
import org.fulib.Fulib;
import org.fulib.builder.ClassBuilder;
import org.fulib.builder.ClassModelBuilder;
import org.fulib.classmodel.ClassModel;

public class CreateModel {

    @Test
    public void createWarehouseModel(){
        ClassModelBuilder mb = Fulib.classModelBuilder("test_ha07.Warehouse.Model", "src/main/java");

        ClassBuilder warehouseClass = mb.buildClass("Warehouse")
                .buildAttribute("name", mb.STRING);

        ClassBuilder warehouseOrderClass = mb.buildClass("WarehouseOrder")
                .buildAttribute("address", mb.STRING)
                .buildAttribute("id", mb.STRING)
                .buildAttribute("product", "WarehouseProduct");

        ClassBuilder warehouseProductClass = mb.buildClass("WarehouseProduct")
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

        lotClass.buildAssociation(palettePlaceClass, "palettePlace", mb.ONE, "lot", mb.ONE);


        ClassModel classModel = mb.getClassModel();
        Fulib.generator().generate(classModel);
    }

    @Test
    public void createShopModel(){
        ClassModelBuilder mb = Fulib.classModelBuilder("test_ha07.Shop.Model", "src/main/java");

         ClassBuilder shopClass = mb.buildClass("Shop");

         ClassBuilder shopCustomerClass = mb.buildClass("ShopCustomer")
                 .buildAttribute("address", mb.STRING)
                 .buildAttribute("name", mb.STRING);

        ClassBuilder shopProductClass = mb.buildClass("ShopProduct")
                .buildAttribute("id", mb.STRING)
                .buildAttribute("inStock", mb.DOUBLE)
                .buildAttribute("name", mb.STRING)
                .buildAttribute("price", mb.DOUBLE);

        ClassBuilder shopOrderClass = mb.buildClass("ShopOrder")
                .buildAttribute("id", mb.STRING);


        shopClass.buildAssociation(shopProductClass,"products", mb.MANY, "shop", mb.ONE);
        shopClass.buildAssociation(shopCustomerClass, "customers", mb.MANY, "shop", mb.ONE);
        shopClass.buildAssociation(shopOrderClass, "orders", mb.MANY, "shop", mb.ONE);
        shopCustomerClass.buildAssociation(shopOrderClass, "orders", mb.MANY, "shopCustomer", mb.ONE);
        shopOrderClass.buildAssociation(shopProductClass, "products", mb.MANY, "orders", mb.MANY);

        ClassModel classModel = mb.getClassModel();
        Fulib.generator().generate(classModel);
    }
}