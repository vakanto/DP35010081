package ha07_ha08.Shop.Model;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class ShopProduct  
{

   public static final String PROPERTY_id = "id";

   private String id;

   public String getId()
   {
      return id;
   }

   public ShopProduct setId(String value)
   {
      if (value == null ? this.id != null : ! value.equals(this.id))
      {
         String oldValue = this.id;
         this.id = value;
         firePropertyChange("id", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_inStock = "inStock";

   private double inStock;

   public double getInStock()
   {
      return inStock;
   }

   public ShopProduct setInStock(double value)
   {
      if (value != this.inStock)
      {
         double oldValue = this.inStock;
         this.inStock = value;
         firePropertyChange("inStock", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_name = "name";

   private String name;

   public String getName()
   {
      return name;
   }

   public ShopProduct setName(String value)
   {
      if (value == null ? this.name != null : ! value.equals(this.name))
      {
         String oldValue = this.name;
         this.name = value;
         firePropertyChange("name", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_price = "price";

   private double price;

   public double getPrice()
   {
      return price;
   }

   public ShopProduct setPrice(double value)
   {
      if (value != this.price)
      {
         double oldValue = this.price;
         this.price = value;
         firePropertyChange("price", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_shop = "shop";

   private Shop shop = null;

   public Shop getShop()
   {
      return this.shop;
   }

   public ShopProduct setShop(Shop value)
   {
      if (this.shop != value)
      {
         Shop oldValue = this.shop;
         if (this.shop != null)
         {
            this.shop = null;
            oldValue.withoutProducts(this);
         }
         this.shop = value;
         if (value != null)
         {
            value.withProducts(this);
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

   public ShopProduct withOrders(Object... value)
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
               ((ShopOrder)item).withProducts(this);
               firePropertyChange("orders", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public ShopProduct withoutOrders(Object... value)
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
               ((ShopOrder)item).withoutProducts(this);
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

      result.append(" ").append(this.getId());
      result.append(" ").append(this.getName());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setShop(null);

      this.withoutOrders(this.getOrders().clone());


   }


}