package ha07.Warehouse.Model;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class PalettePlace  
{

   public static final String PROPERTY_column = "column";

   private double column;

   public double getColumn()
   {
      return column;
   }

   public PalettePlace setColumn(double value)
   {
      if (value != this.column)
      {
         double oldValue = this.column;
         this.column = value;
         firePropertyChange("column", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_id = "id";

   private String id;

   public String getId()
   {
      return id;
   }

   public PalettePlace setId(String value)
   {
      if (value == null ? this.id != null : ! value.equals(this.id))
      {
         String oldValue = this.id;
         this.id = value;
         firePropertyChange("id", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_row = "row";

   private double row;

   public double getRow()
   {
      return row;
   }

   public PalettePlace setRow(double value)
   {
      if (value != this.row)
      {
         double oldValue = this.row;
         this.row = value;
         firePropertyChange("row", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_warehouse = "warehouse";

   private Warehouse warehouse = null;

   public Warehouse getWarehouse()
   {
      return this.warehouse;
   }

   public PalettePlace setWarehouse(Warehouse value)
   {
      if (this.warehouse != value)
      {
         Warehouse oldValue = this.warehouse;
         if (this.warehouse != null)
         {
            this.warehouse = null;
            oldValue.withoutPlaces(this);
         }
         this.warehouse = value;
         if (value != null)
         {
            value.withPlaces(this);
         }
         firePropertyChange("warehouse", oldValue, value);
      }
      return this;
   }



   public static final String PROPERTY_paletteplace = "paletteplace";

   private Lot paletteplace = null;

   public Lot getPaletteplace()
   {
      return this.paletteplace;
   }

   public PalettePlace setPaletteplace(Lot value)
   {
      if (this.paletteplace != value)
      {
         Lot oldValue = this.paletteplace;
         if (this.paletteplace != null)
         {
            this.paletteplace = null;
            oldValue.setLot(null);
         }
         this.paletteplace = value;
         if (value != null)
         {
            value.setLot(this);
         }
         firePropertyChange("paletteplace", oldValue, value);
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
      this.setWarehouse(null);
      this.setPaletteplace(null);

   }


}