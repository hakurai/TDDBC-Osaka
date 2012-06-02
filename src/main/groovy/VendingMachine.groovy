class VendingMachine {

    def validBillSet = [10, 50, 100, 500, 1000] as Set
    def total = 0
    def priceMap = [:]
    def stockMap = [:]

    VendingMachine() {
        setPrice("コーラ", 120)
        5.times {
            store(new Juice("コーラ"))
        }

    }

    def receive(def o) {
        if (validBillSet.contains(o)) {
            total += o
            0
        } else {
            o
        }
    }

    Integer payBack() {
        def result = total
        total = 0
        result
    }

    void store(Juice juice) {
        def list = stockMap.get(juice.name)
        if (list == null) {
            list = []
            stockMap.put(juice.name, list)
        } else {

        }
        list.add(juice)
    }

    Integer stock(String name) {
        def list = stockMap.get(name)

        list == null ? 0 : list.size()
    }

    void setPrice(String name, Integer price) {
        priceMap.put(name, price)
    }

    Integer getPrice(String s) {
        priceMap.get(s)
    }
}
