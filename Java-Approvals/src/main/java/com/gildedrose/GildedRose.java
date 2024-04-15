package com.gildedrose;

import java.util.Arrays;

class GildedRose {

    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void processDay() {
        Arrays.stream(items)
            .filter(item -> !item.name.equals(SULFURAS))
            .forEach(item -> {
                item.sellIn--;
                updateQuality(item);
            });
    }

    private void updateQuality(Item item) {
        if (isDefaultItem(item)) {
            decreaseQuality(item, isExpired(item));
        }
        if (item.name.equals(AGED_BRIE)) {
            increaseQualityForAgedBrie(item, isExpired(item));
        }
        if (item.name.equals(BACKSTAGE_PASS)) {
            if (item.sellIn < 5) {
                increaseQualityForBackstagePass(item, 3);
            } else if (item.sellIn < 10) {
                increaseQualityForBackstagePass(item, 2);
            }
            if (item.sellIn >= 10) {
                increaseQualityForBackstagePass(item, 1);
            }
            if (isExpired(item)) {
                item.quality = 0;
            }
        }
    }

    private boolean isExpired(Item item) {
        return item.sellIn < 0;
    }

    private boolean isDefaultItem(Item item) {
        return !item.name.equals(AGED_BRIE) && !item.name.equals(BACKSTAGE_PASS);
    }

    private void decreaseQuality(Item item, boolean expired) {
        if (item.quality > 0) {
            item.quality--;
        }
        if (expired && item.quality > 0) {
            item.quality--;
        }
    }

    private void increaseQualityForAgedBrie(Item item, boolean expired) {
        if (item.quality + 1 > 50) {
            item.quality = 50;
        } else {
            item.quality++;
        }
        if (expired) {
            if (item.quality + 1 > 50) {
                item.quality = 50;
            } else {
                item.quality++;
            }
        }
    }

    private void increaseQualityForBackstagePass(Item item, int amount) {
        if (item.quality + amount > 50) {
            item.quality = 50;
        } else {
            item.quality += amount;
        }
    }
}
