package com.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest {

    @Test
    public void appConstructorInsertsItems() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
        assertEquals(items.length,app.items.length);
    }
    
    @Test
    public void appUpdateQualityUpdatesAll() {
        Item[] items = new Item[] { 
        		new Item("foo", 2, 2),
        		new Item("foo", 3, 3) 
        	};
        
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(1, app.items[0].sellIn);
        assertEquals(2, app.items[1].sellIn);
    }
    
    @Test
    public void itemUpdateDecrementsQualityByOne_PositiveSellIn() {
    	Item[] items = new Item[] { new Item("foo", 1, 1) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0,app.items[0].quality);
    }
    
    @Test
    public void itemUpdateDecrementsQualityByTwo_ZeroOrNegativeSellIn() {
    	Item[] items = new Item[] { new Item("foo", 0, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(8,app.items[0].quality);
    }
    
    @Test
    public void itemQualityNeverBecomesNegative() {
    	Item[] items = new Item[] { 
    			new Item("foo", 0, 1),
    			new Item("foo", 0, 0)
    			};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0,app.items[0].quality);
        assertEquals(0,app.items[1].quality);
    }
    
    @Test
    public void itemQualityDoesntDecreaseIfZeroOrLess() {
    	Item[] items = new Item[] { 
    			new Item("foo", 0, 0),
    			new Item("foo", 0, -1)
    			};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0,app.items[0].quality);
        assertEquals(-1,app.items[1].quality);
    }
    
    @Test
    public void itemQualityAbove50_DoesNotGetClamped() {
    	Item[] items = new Item[] { 
    			new Item("foo", 1, 55),
    			new Item("foo", 0, 100)
    			};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(54,app.items[0].quality);
        assertEquals(98,app.items[1].quality);
    }
    
    @Test
    public void itemAgedBrieActuallyIncreasesInQualityByOne_PositiveSellIn() {
    	Item[] items = new Item[] { 
    			new Item("Aged Brie", 10, 10),
    			};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(11,app.items[0].quality);
    }
    
    @Test
    public void itemAgedBrieActuallyIncreasesInQualityByTwo_NegativeSellIn() {
    	Item[] items = new Item[] { 
    			new Item("Aged Brie", 0, 10),
    			};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(12,app.items[0].quality);
    }
    
    @Test
    public void itemQualityNeverIncreasesToMoreThan50_AgedBrie()
    {
    	Item[] items = new Item[] { 
    			new Item("Aged Brie", -1, 49),
    			new Item("Aged Brie", -1, 50),
    			new Item("Aged Brie", 1, 50),
    			new Item("Aged Brie", 1, 49),
    			};
        GildedRose app = new GildedRose(items);
                
        app.updateQuality();
        
        int[] qualities = GetArrayOfQualities(app.items);
        int[] expectedOutput = GetArrayOfInt(50,app.items.length);
        assertArrayEquals(expectedOutput,qualities);
    }
    
    @Test
    public void itemQualityNeverIncreasesToMoreThan50_BackstagePass()
    {
    	Item[] items = new Item[] { 
    			new Item("Backstage passes to a TAFKAL80ETC concert", 5, 50),
    			new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
    			new Item("Backstage passes to a TAFKAL80ETC concert", 11, 50),
    			};
        GildedRose app = new GildedRose(items);
                
        app.updateQuality();
        
        int[] qualities = GetArrayOfQualities(app.items);
        int[] expectedOutput = GetArrayOfInt(50,app.items.length);
        assertArrayEquals(expectedOutput,qualities);
    }
    
    @Test
    public void itemSulfurasNeverChanges_Quality()
    {
    	Item[] items = new Item[] { 
    			new Item("Sulfuras, Hand of Ragnaros", -1, 0),
    			new Item("Sulfuras, Hand of Ragnaros", 1, 0),
    			new Item("Sulfuras, Hand of Ragnaros", 0, 80),
    			new Item("Sulfuras, Hand of Ragnaros", 1, 80),
    			new Item("Sulfuras, Hand of Ragnaros", -1, 80),
    		};
    	
        GildedRose app = new GildedRose(items);
                
        int[] qualities = GetArrayOfQualities(app.items);
        int[] expectedOutput = GetArrayOfInt(80,app.items.length);
        
        //Sulfuras is "always 80" however app should still not modify this value
        //First entry tests that quality remains at the setting that was input
        expectedOutput[0] = 0;
        expectedOutput[1] = 0;
        
        app.updateQuality();

        assertArrayEquals(expectedOutput,qualities);
    }
    
    @Test
    public void itemSulfurasNeverChanges_SellIn()
    {
    	Item[] items = new Item[] { 
    			new Item("Sulfuras, Hand of Ragnaros", -1, 0),
    			new Item("Sulfuras, Hand of Ragnaros", 1, 0),
    			new Item("Sulfuras, Hand of Ragnaros", 0, 80),
    			new Item("Sulfuras, Hand of Ragnaros", 1, 80),
    			new Item("Sulfuras, Hand of Ragnaros", -1, 80),
    		};
    	
        GildedRose app = new GildedRose(items);
                
        int[] expectedOutput = GetArrayOfSellIn(app.items);
        app.updateQuality();
        int[] actualOutput = GetArrayOfSellIn(app.items);
        
        //SellIn values before and after update should remain the same
        assertArrayEquals(expectedOutput,actualOutput);
    }
    
    @Test
    public void itemBackstagePassQualityIncreases_ByOne_SellinGreaterThan10()
    {
    	Item[] items = new Item[] { 
    			new Item("Backstage passes to a TAFKAL80ETC concert", 11, 20),
    			};
    	
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(21,app.items[0].quality);
    }
    
    @Test
    public void itemBackstagePassQualityIncreases_ByTwo_SellinGreaterThan5LessThan11()
    {
    	Item[] items = new Item[] { 
    			new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20),
    			};
    	
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(22,app.items[0].quality);
    }
    
    @Test
    public void itemBackstagePassQualityIncreases_ByThree_SellInLessThan6()
    {
    	Item[] items = new Item[] { 
    			new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20),
    			};
    	
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(23,app.items[0].quality);
    }
    
    @Test
    public void itemBackstagePassQuality_ToZero_SellInNegative()
    {
    	Item[] items = new Item[] { 
    			new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20),
    			};
    	
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(0,app.items[0].quality);
    }
    
    @Test
    public void itemUpdateDecrementsQualityByTwo_Conjured_PositiveSellIn()
    {
    	Item[] items = new Item[] { 
    			new Item("Conjured Item", 10, 20),
    		};
    	
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(18,app.items[0].quality);
    }
    
    @Test
    public void itemUpdateDecrementsQualityByFour_Conjured_NegativeSellIn()
    {
    	Item[] items = new Item[] { 
    			new Item("Conjured Item", 0, 20),
			};
    	
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(16,app.items[0].quality);
    }
    
    @Test
    public void itemQualityDoesntDecreaseBeyondZero_Conjured()
    {
    	Item[] items = new Item[] {
    			new Item("Conjured Item", -1, 1),
			};
    	
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(0,app.items[0].quality);
    }
    
    private static int[] GetArrayOfQualities(Item[] items) {
    	int[] qualities = new int[items.length];
    	for ( int i = 0 ; i < qualities.length; i++) {
    		qualities[i] = items[i].quality;
    	}
    	return qualities;
    }
    
    private static int[] GetArrayOfSellIn(Item[] items) {
    	int[] qualities = new int[items.length];
    	for ( int i = 0 ; i < qualities.length; i++) {
    		qualities[i] = items[i].sellIn;
    	}
    	return qualities;
    }
    
    private static int[] GetArrayOfInt(int value, int length) {
    	int[] qualities = new int[length];
    	for ( int i = 0 ; i < qualities.length; i++) {
    		qualities[i] = value;
    	}
    	return qualities;
    }
}
