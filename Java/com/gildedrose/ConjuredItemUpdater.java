package com.gildedrose;

public class ConjuredItemUpdater implements IItemUpdater
{
	public void UpdateItem(Item item)
	{
		item.sellIn--;
		
		//Maintaining legacy behavior of not updating quality if it is below zero
		if ( item.quality <= 0 )
		{
			return;
		}
		
		if ( item.sellIn < 0 )
		{
			item.quality -= 4;
		}
		else
		{
			item.quality -= 2;
		}
		
		if ( item.quality < 0 )
		{
			item.quality = 0;
		}
	}
}