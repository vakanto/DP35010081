package ha07_ha08.Shop.Model;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class ShopCustomer  
{

   public static final String PROPERTY_address = "address";

   private String address;

   public String getAddress()
   {
      return address;
   }

   public ShopCustomer setAddress(String value)
   {
      if (value == null ? this.address != null : ! value.equals(this.address))
      {
         String oldValue = this.address;
         this.address = value;
         firePropertyChange("address", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_name = "name";

   private String name;

   public String getName()
   {
      return name;
   }

   public ShopCustomer setName(String value)
   {
      if (value == null ? this.name != null : ! value.equals(this.name))
      {
         String oldValue = this.name;
         this.name = value;
         firePropertyChange("name", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_shop = "shop";

   private Shop shop = null;

   public Shop getShop()
   {
      return this.shop;
   }

   public ShopCustomer setShop(Shop value)
   {
      if (this.shop != value)
      {
         Shop oldValue = this.shop;
         if (this.shop != null)
         {
            this.shop = null;
            oldValue.withoutCustomers(this);
         }
         this.shop = value;
         if (value != null)
         {
            value.withCustomers(this);
         }
         firePropertyChange("shop", oldValue, value);
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

   public ShopCustomer withOrders(Object... value)
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
               ((ShopOrder)item).setShopCustomer(this);
               firePropertyChange("orders", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public ShopCustomer withoutOrders(Object... value)
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
               ((ShopOrder)item).setShopCustomer(null);
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

   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();

      result.append(" ").append(this.getAddress());
      result.append(" ").append(this.getName());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setShop(null);

      this.withoutOrders(this.getOrders().clone());


   }


}