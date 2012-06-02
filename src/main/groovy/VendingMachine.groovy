class VendingMachine {

    def validBillSet = [10, 50, 100, 500, 1000] as Set
    def total = 0

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
}
