package com.gildedrose;

public class NormalItemUpdater implements IItemUpdater
{
	public void UpdateItem(Item item)
	{
		item.sellIn--;
		
		//Don't decrement quality if we're already at or below zero
		//This also prevents doing the negative check that the end
		//We do it this way to maintain legacy behavior
		if ( item.quality <= 0 )
		{
			return;
		}
		
		if ( item.sellIn < 0 )
		{
			item.quality -= 2;
		}
		else
		{
			item.quality -= 1;
		}
		
		if ( item.quality < 0 )
		{
			item.quality = 0;
		}
	}
}