import spock.lang.Specification
import spock.lang.Unroll

class VendingMachineTest extends Specification {

    @Unroll
    def '#b 円を投入すると #exp 円が返ってくる'() {
        setup:
        def vendingMachine = new VendingMachine()

        expect:
        exp == vendingMachine.receive(b)

        where:
        exp     | b
        10000 | 10000
        5000  | 5000
        2000  | 2000
        0     | 1000
        0     | 500
        0     | 100
        0     | 50
        0     | 10
        5     | 5
        1     | 1
    }

    @Unroll
    def "#b 円投入した状態で払い戻しを行うと #exp 円が返ってくる"() {
        setup:
        def vendingMachine = new VendingMachine()
        vendingMachine.receive(b)

        expect:
        exp == vendingMachine.payBack()

        where:
        exp    | b
        1000 | 1000
        500  | 500

    }

    def "110円投入した状態で払い戻しを行うと110円が返ってくる"() {
        setup:
        def vendingMachine = new VendingMachine()

        when:
        vendingMachine.receive(100)
        vendingMachine.receive(10)

        then:
        110 == vendingMachine.payBack()


    }

    def "払い戻しをしたあとに再度払い戻しをすると0が返却される"() {
        setup:
        def vendingMachine = new VendingMachine()
        vendingMachine.receive(100)

        when:
        vendingMachine.payBack()

        then:
        0 == vendingMachine.payBack()

    }

    @Unroll
    def "#b 円に合計金額を取得する"() {
        setup:
        def vendingMachine = new VendingMachine()
        b.each {
            vendingMachine.receive(it)
        }

        expect:
        exp == vendingMachine.total

        where:
        exp    | b
        110  | [10, 100]
        1200 | [1000, 100, 50, 50]
        100  | [100, 1]
    }

    def "ジュースを格納する"() {
        setup:
        def vendingMachine = new VendingMachine()
        def juice = new Juice("コーラ")

        expect:
        vendingMachine.store(juice)

    }

    def "ジュースの在庫を取得する"() {
        setup:
        def vendingMachine = new VendingMachine()

        expect:
        5 == vendingMachine.getStock("コーラ")

    }

    def "ジュースの値段を取得する"() {
        setup:
        def vendingMachine = new VendingMachine()

        when:
        def price = vendingMachine.getPrice("コーラ")

        then:
        price == 120
    }

    @Unroll
    def "#b 円 投入した状態でコーラが購入できるかどうかを返す"() {
        setup:
        def vendingMachine = new VendingMachine()
        b.each {
            vendingMachine.receive(it)
        }

        expect:
        exp == vendingMachine.canBuy("コーラ")

        where:
        exp     | b
        true  | [100, 10, 10]
        false | [100]

    }

    def "ジュースを購入するとジュースを返す"() {
        setup:
        def vendingMachine = new VendingMachine()


        when:
        vendingMachine.receive(100)
        vendingMachine.receive(10)
        vendingMachine.receive(10)
        def juice = vendingMachine.buy("コーラ")

        then:
        juice.name == "コーラ"
    }

    def "ジュースを購入した後のつり銭を返す"() {
        setup:
        def vendingMachine = new VendingMachine()


        when:
        buyCola(vendingMachine)

        then:
        0 == vendingMachine.payBack()
    }

    def "ジュースを購入すると在庫が減る"() {
        setup:
        def vendingMachine = new VendingMachine()


        when:
        buyCola(vendingMachine)

        then:
        4 == vendingMachine.getStock("コーラ")
    }

    def "在庫切れの状態で購入すると null を返す"() {
        setup:
        def vendingMachine = new VendingMachine()
        5.times {
            buyCola(vendingMachine)
        }

        when:
        vendingMachine.receive(100)
        vendingMachine.receive(10)
        vendingMachine.receive(10)


        then:
        null == vendingMachine.buy("コーラ")
    }


    void buyCola(def vendingMachine) {
        vendingMachine.receive(100)
        vendingMachine.receive(10)
        vendingMachine.receive(10)
        vendingMachine.buy("コーラ")
    }

}