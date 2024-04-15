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
            decreaseQuality(item);
        } else {
            if (item.name.equals(BACKSTAGE_PASS)) {
                if (item.sellIn < 5) {
                    increaseQuality(item, 3);
                } else if (item.sellIn < 10) {
                    increaseQuality(item, 2);
                }
                if (item.sellIn >= 10) {
                    increaseQuality(item, 1);
                }
            }
            if (item.name.equals(AGED_BRIE)) {
                increaseQuality(item, 1);
            }
        }

        if (isExpired(item)) {
            if (isDefaultItem(item)) {
                decreaseQuality(item);
            } else {
                increaseQuality(item, 1);
            }
            if (item.name.equals(BACKSTAGE_PASS)) {
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

    private void decreaseQuality(Item item) {
        if (item.quality > 0) {
            item.quality--;
        }
    }

    private void increaseQuality(Item item, int amount) {
        if (item.quality + amount > 50) {
            item.quality = 50;
        } else {
            item.quality += amount;
        }
    }
}
