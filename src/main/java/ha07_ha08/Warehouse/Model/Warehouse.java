package ha07_ha08.Warehouse.Model;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Warehouse  
{

   public static final String PROPERTY_name = "name";

   private String name;

   public String getName()
   {
      return name;
   }

   public Warehouse setName(String value)
   {
      if (value == null ? this.name != null : ! value.equals(this.name))
      {
         String oldValue = this.name;
         this.name = value;
         firePropertyChange("name", oldValue, value);
      }
      return this;
   }


   public static final java.util.ArrayList<PalettePlace> EMPTY_places = new java.util.ArrayList<PalettePlace>()
   { @Override public boolean add(PalettePlace value){ throw new UnsupportedOperationException("No direct add! Use xy.withPlaces(obj)"); }};


   public static final String PROPERTY_places = "places";

   private java.util.ArrayList<PalettePlace> places = null;

   public java.util.ArrayList<PalettePlace> getPlaces()
   {
      if (this.places == null)
      {
         return EMPTY_places;
      }

      return this.places;
   }

   public Warehouse withPlaces(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withPlaces(i);
            }
         }
         else if (item instanceof PalettePlace)
         {
            if (this.places == null)
            {
               this.places = new java.util.ArrayList<PalettePlace>();
            }
            if ( ! this.places.contains(item))
            {
               this.places.add((PalettePlace)item);
               ((PalettePlace)item).setWarehouse(this);
               firePropertyChange("places", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Warehouse withoutPlaces(Object... value)
   {
      if (this.places == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutPlaces(i);
            }
         }
         else if (item instanceof PalettePlace)
         {
            if (this.places.contains(item))
            {
               this.places.remove((PalettePlace)item);
               ((PalettePlace)item).setWarehouse(null);
               firePropertyChange("places", item, null);
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


      return result.substring(1);
   }

   public void removeYou()
   {
      this.withoutOrders(this.getOrders().clone());


      this.withoutProducts(this.getProducts().clone());


      this.withoutPlaces(this.getPlaces().clone());


   }


   public static final java.util.ArrayList<WarehouseProduct> EMPTY_products = new java.util.ArrayList<WarehouseProduct>()
   { @Override public boolean add(WarehouseProduct value){ throw new UnsupportedOperationException("No direct add! Use xy.withProducts(obj)"); }};


   public static final String PROPERTY_products = "products";

   private java.util.ArrayList<WarehouseProduct> products = null;

   public java.util.ArrayList<WarehouseProduct> getProducts()
   {
      if (this.products == null)
      {
         return EMPTY_products;
      }

      return this.products;
   }

   public Warehouse withProducts(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withProducts(i);
            }
         }
         else if (item instanceof WarehouseProduct)
         {
            if (this.products == null)
            {
               this.products = new java.util.ArrayList<WarehouseProduct>();
            }
            if ( ! this.products.contains(item))
            {
               this.products.add((WarehouseProduct)item);
               ((WarehouseProduct)item).setWarehouse(this);
               firePropertyChange("products", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Warehouse withoutProducts(Object... value)
   {
      if (this.products == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutProducts(i);
            }
         }
         else if (item instanceof WarehouseProduct)
         {
            if (this.products.contains(item))
            {
               this.products.remove((WarehouseProduct)item);
               ((WarehouseProduct)item).setWarehouse(null);
               firePropertyChange("products", item, null);
            }
         }
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

   public Warehouse withOrders(Object... value)
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
               ((WarehouseOrder)item).setWarehouseOrder(this);
               firePropertyChange("orders", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Warehouse withoutOrders(Object... value)
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
               ((WarehouseOrder)item).setWarehouseOrder(null);
               firePropertyChange("orders", item, null);
            }
         }
      }
      return this;
   }


}