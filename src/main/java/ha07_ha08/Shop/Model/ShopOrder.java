package ha07_ha08.Shop.Model;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class ShopOrder  
{

   public static final String PROPERTY_id = "id";

   private String id;

   public String getId()
   {
      return id;
   }

   public ShopOrder setId(String value)
   {
      if (value == null ? this.id != null : ! value.equals(this.id))
      {
         String oldValue = this.id;
         this.id = value;
         firePropertyChange("id", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_shop = "shop";

   private Shop shop = null;

   public Shop getShop()
   {
      return this.shop;
   }

   public ShopOrder setShop(Shop value)
   {
      if (this.shop != value)
      {
         Shop oldValue = this.shop;
         if (this.shop != null)
         {
            this.shop = null;
            oldValue.withoutOrders(this);
         }
         this.shop = value;
         if (value != null)
         {
            value.withOrders(this);
         }
         firePropertyChange("shop", oldValue, value);
      }
      return this;
   }



   public static final String PROPERTY_shopCustomer = "shopCustomer";

   private ShopCustomer shopCustomer = null;

   public ShopCustomer getShopCustomer()
   {
      return this.shopCustomer;
   }

   public ShopOrder setShopCustomer(ShopCustomer value)
   {
      if (this.shopCustomer != value)
      {
         ShopCustomer oldValue = this.shopCustomer;
         if (this.shopCustomer != null)
         {
            this.shopCustomer = null;
            oldValue.withoutOrders(this);
         }
         this.shopCustomer = value;
         if (value != null)
         {
            value.withOrders(this);
         }
         firePropertyChange("shopCustomer", oldValue, value);
      }
      return this;
   }



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

   public ShopOrder withProducts(Object... value)
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
               ((ShopProduct)item).withOrders(this);
               firePropertyChange("products", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public ShopOrder withoutProducts(Object... value)
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
               ((ShopProduct)item).withoutOrders(this);
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

   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();

      result.append(" ").append(this.getId());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setShop(null);
      this.setShopCustomer(null);

      this.withoutProducts(this.getProducts().clone());


   }


}