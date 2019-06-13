package ha07.Warehouse.Model;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class WarehouseProduce 
{

   public static final String PROPERTY_name = "name";

   private String name;

   public String getName()
   {
      return name;
   }

   public WarehouseProduce setName(String value)
   {
      if (value == null ? this.name != null : ! value.equals(this.name))
      {
         String oldValue = this.name;
         this.name = value;
         firePropertyChange("name", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_id = "id";

   private String id;

   public String getId()
   {
      return id;
   }

   public WarehouseProduce setId(String value)
   {
      if (value == null ? this.id != null : ! value.equals(this.id))
      {
         String oldValue = this.id;
         this.id = value;
         firePropertyChange("id", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_warehouse = "warehouse";

   private Warehouse warehouse = null;

   public Warehouse getWarehouse()
   {
      return this.warehouse;
   }

   public WarehouseProduce setWarehouse(Warehouse value)
   {
      if (this.warehouse != value)
      {
         Warehouse oldValue = this.warehouse;
         if (this.warehouse != null)
         {
            this.warehouse = null;
            oldValue.withoutProducts(this);
         }
         this.warehouse = value;
         if (value != null)
         {
            value.withProducts(this);
         }
         firePropertyChange("warehouse", oldValue, value);
      }
      return this;
   }



   public static final java.util.ArrayList<WarehouseOrder> EMPTY_orders = new java.util.ArrayList<WarehouseOrder>()
   { @Override public boolean add(WarehouseOrder value){ throw new UnsupportedOperationException("No direct add! Use xy.withOrders(obj)"); }};


   public static final String PROPERTY_orders = "orders";

   private java.util.ArrayList<WarehouseOrder> orders = null;

   public java.util.ArrayList<WarehouseOrder> getOrders()
   {
      if (this.orders == null)
      {
         return EMPTY_orders;
      }

      return this.orders;
   }

   public WarehouseProduce withOrders(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withOrders(i);
            }
         }
         else if (item instanceof WarehouseOrder)
         {
            if (this.orders == null)
            {
               this.orders = new java.util.ArrayList<WarehouseOrder>();
            }
            if ( ! this.orders.contains(item))
            {
               this.orders.add((WarehouseOrder)item);
               ((WarehouseOrder)item).withProducts(this);
               firePropertyChange("orders", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public WarehouseProduce withoutOrders(Object... value)
   {
      if (this.orders == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutOrders(i);
            }
         }
         else if (item instanceof WarehouseOrder)
         {
            if (this.orders.contains(item))
            {
               this.orders.remove((WarehouseOrder)item);
               ((WarehouseOrder)item).withoutProducts(this);
               firePropertyChange("orders", item, null);
            }
         }
      }
      return this;
   }


   public static final java.util.ArrayList<Lot> EMPTY_lots = new java.util.ArrayList<Lot>()
   { @Override public boolean add(Lot value){ throw new UnsupportedOperationException("No direct add! Use xy.withLots(obj)"); }};


   public static final String PROPERTY_lots = "lots";

   private java.util.ArrayList<Lot> lots = null;

   public java.util.ArrayList<Lot> getLots()
   {
      if (this.lots == null)
      {
         return EMPTY_lots;
      }

      return this.lots;
   }

   public WarehouseProduce withLots(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withLots(i);
            }
         }
         else if (item instanceof Lot)
         {
            if (this.lots == null)
            {
               this.lots = new java.util.ArrayList<Lot>();
            }
            if ( ! this.lots.contains(item))
            {
               this.lots.add((Lot)item);
               ((Lot)item).setWareHouseProduct(this);
               firePropertyChange("lots", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public WarehouseProduce withoutLots(Object... value)
   {
      if (this.lots == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutLots(i);
            }
         }
         else if (item instanceof Lot)
         {
            if (this.lots.contains(item))
            {
               this.lots.remove((Lot)item);
               ((Lot)item).setWareHouseProduct(null);
               firePropertyChange("lots", item, null);
            }
         }
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

      result.append(" ").append(this.getName());
      result.append(" ").append(this.getId());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setWarehouse(null);

      this.withoutOrders(this.getOrders().clone());


      this.withoutLots(this.getLots().clone());


   }


}