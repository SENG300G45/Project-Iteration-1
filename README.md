# Project-Iteration-1

# Customer scans an item
We have the scanItem method for item scanning use case, where it takes in a purchasable item, scans its barcode, and stores it into itemBarcode. It also searches the item’s barcode in the catalog.
# Customer places item in bagging area
We have the placeInBaggingArea method for when a customer places an item in the bagging area use case, where it takes in a purchasable item, shows the item's weight, and checks if items’ weight matches with the catalog’s.
# Customer wishes to checkout
Check if  the customer is paying with coins, or banknotes and simulate the customer adding the coin until the bill is paid.
# Customer pays with a coin
Customer adds a coin using the addCoin function. If the coin is invalid, the attendant is called.
# Customer pays with a banknote
Customer adds a banknote using the addBanknote function. If the banknote is invalid, the attendant is called.
 
StationInteractor.java interacts with the hardware.

