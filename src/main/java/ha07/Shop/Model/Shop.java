package ha07.Shop.Model;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Shop 
{

   public static final java.util.ArrayList<ShopProduct> EMPTY_products = new java.util.ArrayList<ShopProduct>()
   { @Override public boolean add(ShopProduct value){ throw new UnsupportedOperationException("No direct add! Use xy.withProducts(obj)"); }};


   public static final String PROPERTY_products = "products";

   private java.util.ArrayList<ShopProduct> products = null;

   public java.util.ArrayList<ShopProduct> getProducts()
   {
      if (this.products == null)
      {
         return EMPTY_products;
      }

      return this.products;
   }

   public Shop withProducts(Object... value)
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
         else if (item instanceof ShopProduct)
         {
            if (this.products == null)
            {
               this.products = new java.util.ArrayList<ShopProduct>();
            }
            if ( ! this.products.contains(item))
            {
               this.products.add((ShopProduct)item);
               ((ShopProduct)item).setShop(this);
               firePropertyChange("products", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Shop withoutProducts(Object... value)
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
         else if (item instanceof ShopProduct)
         {
            if (this.products.contains(item))
            {
               this.products.remove((ShopProduct)item);
               ((ShopProduct)item).setShop(null);
               firePropertyChange("products", item, null);
            }
         }
      }
      return this;
   }


   public static final java.util.ArrayList<ShopCustomer> EMPTY_customers = new java.util.ArrayList<ShopCustomer>()
   { @Override public boolean add(ShopCustomer value){ throw new UnsupportedOperationException("No direct add! Use xy.withCustomers(obj)"); }};


   public static final String PROPERTY_customers = "customers";

   private java.util.ArrayList<ShopCustomer> customers = null;

   public java.util.ArrayList<ShopCustomer> getCustomers()
   {
      if (this.customers == null)
      {
         return EMPTY_customers;
      }

      return this.customers;
   }

   public Shop withCustomers(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withCustomers(i);
            }
         }
         else if (item instanceof ShopCustomer)
         {
            if (this.customers == null)
            {
               this.customers = new java.util.ArrayList<ShopCustomer>();
            }
            if ( ! this.customers.contains(item))
            {
               this.customers.add((ShopCustomer)item);
               ((ShopCustomer)item).setShop(this);
               firePropertyChange("customers", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Shop withoutCustomers(Object... value)
   {
      if (this.customers == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutCustomers(i);
            }
         }
         else if (item instanceof ShopCustomer)
         {
            if (this.customers.contains(item))
            {
               this.customers.remove((ShopCustomer)item);
               ((ShopCustomer)item).setShop(null);
               firePropertyChange("customers", item, null);
            }
         }
      }
      return this;
   }


   public static final java.util.ArrayList<ShopOrder> EMPTY_orders = new java.util.ArrayList<ShopOrder>()
   { @Override public boolean add(ShopOrder value){ throw new UnsupportedOperationException("No direct add! Use xy.withOrders(obj)"); }};


   public static final String PROPERTY_orders = "orders";

   private java.util.ArrayList<ShopOrder> orders = null;

   public java.util.ArrayList<ShopOrder> getOrders()
   {
      if (this.orders == null)
      {
         return EMPTY_orders;
      }

      return this.orders;
   }

   public Shop withOrders(Object... value)
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
         else if (item instanceof ShopOrder)
         {
            if (this.orders == null)
            {
               this.orders = new java.util.ArrayList<ShopOrder>();
            }
            if ( ! this.orders.contains(item))
            {
               this.orders.add((ShopOrder)item);
               ((ShopOrder)item).setShop(this);
               firePropertyChange("orders", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Shop withoutOrders(Object... value)
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
         else if (item instanceof ShopOrder)
         {
            if (this.orders.contains(item))
            {
               this.orders.remove((ShopOrder)item);
               ((ShopOrder)item).setShop(null);
               firePropertyChange("orders", item, null);
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


      this.withoutCustomers(this.getCustomers().clone());


      this.withoutOrders(this.getOrders().clone());


   }


}