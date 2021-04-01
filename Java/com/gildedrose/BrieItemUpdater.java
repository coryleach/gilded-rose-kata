package com.gildedrose;

public class BrieItemUpdater implements IItemUpdater
{
	public void UpdateItem(Item item)
	{
		item.sellIn--;
		
		if ( item.sellIn < 0 )
		{
			//Aged Brie Increases Quality by 2
			item.quality += 2;
		}
		else
		{
			item.quality += 1;
		}
		
		//Never increase over 50
		if ( item.quality > 50 )
		{
			item.quality = 50;
		}
	}
}