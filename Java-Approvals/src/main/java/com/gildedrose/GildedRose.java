package com.gildedrose;

class GildedRose {

    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void processDay() {
        updateSellIn();
        updateQuality();
    }

    private void updateSellIn() {
        for (Item item : items) {
            if (!item.name.equals(SULFURAS)) {
                item.sellIn--;
            }
        }
    }

    private void updateQuality() {
        for (Item item : items) {
            if (!item.name.equals(AGED_BRIE)
                && !item.name.equals(BACKSTAGE_PASS)) {
                if (item.quality > 0) {
                    if (!item.name.equals(SULFURAS)) {
                        decreaseQuality(item);
                    }
                }
            } else {
                if (item.name.equals(BACKSTAGE_PASS) && item.sellIn < 5) {
                    increaseQuality(item, 3);
                } else if (item.name.equals(BACKSTAGE_PASS) && item.sellIn < 10) {
                    increaseQuality(item, 2);
                } else {
                    increaseQuality(item, 1);
                }
            }

            if (item.sellIn < 0) {
                if (!item.name.equals(AGED_BRIE)) {
                    if (!item.name.equals(BACKSTAGE_PASS)) {
                        if (item.quality > 0) {
                            if (!item.name.equals(SULFURAS)) {
                                decreaseQuality(item);
                            }
                        }
                    } else {
                        item.quality = 0;
                    }
                } else {
                    if (item.quality < 50) {
                        increaseQuality(item, 1);
                    }
                }
            }
        }
    }

    private static void decreaseQuality(Item item) {
        item.quality--;
    }

    private static void increaseQuality(Item item, int amount) {
        if (item.quality + amount > 50 ) {
            item.quality = 50;
        } else {
            item.quality += amount;
        }
    }
}
