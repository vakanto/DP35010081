package test_ha07.Warehouse.Model;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Lot  
{

   public static final String PROPERTY_id = "id";

   private String id;

   public String getId()
   {
      return id;
   }

   public Lot setId(String value)
   {
      if (value == null ? this.id != null : ! value.equals(this.id))
      {
         String oldValue = this.id;
         this.id = value;
         firePropertyChange("id", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_lotSize = "lotSize";

   private double lotSize;

   public double getLotSize()
   {
      return lotSize;
   }

   public Lot setLotSize(double value)
   {
      if (value != this.lotSize)
      {
         double oldValue = this.lotSize;
         this.lotSize = value;
         firePropertyChange("lotSize", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_wareHouseProduct = "wareHouseProduct";

   private WarehouseProduct wareHouseProduct = null;

   public WarehouseProduct getWareHouseProduct()
   {
      return this.wareHouseProduct;
   }

   public Lot setWareHouseProduct(WarehouseProduct value)
   {
      if (this.wareHouseProduct != value)
      {
         WarehouseProduct oldValue = this.wareHouseProduct;
         if (this.wareHouseProduct != null)
         {
            this.wareHouseProduct = null;
            oldValue.withoutLots(this);
         }
         this.wareHouseProduct = value;
         if (value != null)
         {
            value.withLots(this);
         }
         firePropertyChange("wareHouseProduct", oldValue, value);
      }
      return this;
   }



   public static final String PROPERTY_palettePlace = "palettePlace";

   private PalettePlace palettePlace = null;

   public PalettePlace getPalettePlace()
   {
      return this.palettePlace;
   }

   public Lot setPalettePlace(PalettePlace value)
   {
      if (this.palettePlace != value)
      {
         PalettePlace oldValue = this.palettePlace;
         if (this.palettePlace != null)
         {
            this.palettePlace = null;
            oldValue.setLot(null);
         }
         this.palettePlace = value;
         if (value != null)
         {
            value.setLot(this);
         }
         firePropertyChange("palettePlace", oldValue, value);
      }
      return this;
   }



   protected PropertyChangeSupport listeners = null;

   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (listeners != null)
      {
         listeners.firePropertyChange(propertyName, oldValue, newValue);
         return true;
      }
      return false;
   }

   public boolean addPropertyChangeListener(PropertyChangeListener listener)
   {
      if (listeners == null)
      {
         listeners = new PropertyChangeSupport(this);
      }
      listeners.addPropertyChangeListener(listener);
      return true;
   }

   public boolean addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
   {
      if (listeners == null)
      {
         listeners = new PropertyChangeSupport(this);
      }
      listeners.addPropertyChangeListener(propertyName, listener);
      return true;
   }

   public boolean removePropertyChangeListener(PropertyChangeListener listener)
   {
      if (listeners != null)
      {
         listeners.removePropertyChangeListener(listener);
      }
      return true;
   }

   public boolean removePropertyChangeListener(String propertyName,PropertyChangeListener listener)
   {
      if (listeners != null)
      {
         listeners.removePropertyChangeListener(propertyName, listener);
      }
      return true;
   }

   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();

      result.append(" ").append(this.getId());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setWareHouseProduct(null);
      this.setPalettePlace(null);

   }


}