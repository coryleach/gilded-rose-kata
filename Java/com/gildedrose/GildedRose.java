package com.gildedrose;

class GildedRose {
	
    Item[] items;

    NormalItemUpdater normalItemUpdater = new NormalItemUpdater();
    BrieItemUpdater brieItemUpdater = new BrieItemUpdater();
    LegendaryItemUpdater legendaryItemUpdater = new LegendaryItemUpdater();
    BackstagePassItemUpdater backstagePassItemUpdater = new BackstagePassItemUpdater();
    ConjuredItemUpdater conjuredItemUpdater = new ConjuredItemUpdater();
    
    public GildedRose(Item[] items) {
        this.items = items;
    }
    
    public IItemUpdater GetItemUpdater(Item item)
    {
    	if ( item.name.contains("Aged Brie") )
    	{
    		return brieItemUpdater;

    	}
    	else if ( item.name.contains("Sulfuras") )
    	{
    		return legendaryItemUpdater;

    	}
    	else if ( item.name.contains("Backstage pass") )
    	{
    		return backstagePassItemUpdater;

    	}
    	else if ( item.name.contains("Conjured") )
    	{
    		return conjuredItemUpdater;
    	}
    	else
    	{
    		return normalItemUpdater;
    	}
    }
    
    public void updateQuality() 
    {
        for (int i = 0; i < items.length; i++) 
        {
        	GetItemUpdater(items[i]).UpdateItem(items[i]);
        }
    }
}
