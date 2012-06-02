import spock.lang.Specification
import spock.lang.Unroll

class VendingMachineTest extends Specification {

    @Unroll
    def '#b 円を投入すると #a 円が返ってくる'() {
        setup:
        def vendingMachine = new VendingMachine()

        expect:
        a == vendingMachine.receive(b)

        where:
        a     | b
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
    def "#b 円投入した状態で払い戻しを行うと #a 円が返ってくる"() {
        setup:
        def vendingMachine = new VendingMachine()
        vendingMachine.receive(b)

        expect:
        a == vendingMachine.payBack()

        where:
        a    | b
        1000 | 1000
        500  | 500

    }

    def "110円投入した状態で払い戻しを行うと110円が返ってくる"() {
        setup:
        def vendingMachine = new VendingMachine()

        when:
        vendingMachine.receive(100)
        vendingMachine.receive(10)
        def a = vendingMachine.payBack()

        then:
        a == 110


    }

    def "払い戻しをしたあとに再度払い戻しをすると0が返却される"() {
        setup:
        def vendingMachine = new VendingMachine()

        when:
        vendingMachine.receive(100)
        vendingMachine.payBack()

        then:
        0 == vendingMachine.payBack()

    }

    def "汎用的に合計金額を取得する"() {
        setup:
        def vendingMachine = new VendingMachine()
        b.each {
            vendingMachine.receive(it)
        }

        expect:
        a == vendingMachine.total

        where:
        a    | b
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
        5 == vendingMachine.stock("コーラ")

    }

    def "ジュースをの値段を取得する"() {
        setup:
        def vendingMachine = new VendingMachine()

        when:
        def price = vendingMachine.getPrice("コーラ")

        then:
        price == 120
    }
}