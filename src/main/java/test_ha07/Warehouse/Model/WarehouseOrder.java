package test_ha07.Warehouse.Model;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class WarehouseOrder  
{

   public static final String PROPERTY_address = "address";

   private String address;

   public String getAddress()
   {
      return address;
   }

   public WarehouseOrder setAddress(String value)
   {
      if (value == null ? this.address != null : ! value.equals(this.address))
      {
         String oldValue = this.address;
         this.address = value;
         firePropertyChange("address", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_id = "id";

   private String id;

   public String getId()
   {
      return id;
   }

   public WarehouseOrder setId(String value)
   {
      if (value == null ? this.id != null : ! value.equals(this.id))
      {
         String oldValue = this.id;
         this.id = value;
         firePropertyChange("id", oldValue, value);
      }
      return this;
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

   public WarehouseOrder withProducts(Object... value)
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
               ((WarehouseProduct)item).withOrders(this);
               firePropertyChange("products", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public WarehouseOrder withoutProducts(Object... value)
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
               ((WarehouseProduct)item).withoutOrders(this);
               firePropertyChange("products", item, null);
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

   public void removeYou()
   {
      this.withoutProducts(this.getProducts().clone());


   }

   public static final String PROPERTY_product = "product";

   private WarehouseProduct product;

   public WarehouseProduct getProduct()
   {
      return product;
   }

   public WarehouseOrder setProduct(WarehouseProduct value)
   {
      if (value != this.product)
      {
         WarehouseProduct oldValue = this.product;
         this.product = value;
         firePropertyChange("product", oldValue, value);
      }
      return this;
   }


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();

      result.append(" ").append(this.getAddress());
      result.append(" ").append(this.getId());


      return result.substring(1);
   }

}