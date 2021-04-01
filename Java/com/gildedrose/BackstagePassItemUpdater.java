package com.gildedrose;

public class BackstagePassItemUpdater implements IItemUpdater
{
	public void UpdateItem(Item item)
	{
		item.sellIn--;
		
		if ( item.sellIn >= 10 )
		{
			item.quality += 1;
		}
		else if ( item.sellIn >= 5 )
		{
			item.quality += 2;
		}
		else if ( item.sellIn >= 0 )
		{
			item.quality += 3;
		}
		else
		{
			item.quality = 0;
		}
		
		if ( item.quality > 50 )
		{
			item.quality = 50;
		}
	}
}