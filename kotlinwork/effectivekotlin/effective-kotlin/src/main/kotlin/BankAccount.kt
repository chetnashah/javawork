import java.lang.Exception

class BankAccount: Comparable<BankAccount> {
    var balance = 0.0
        private set

    fun deposit(depositAmount: Double) {
        balance += depositAmount;
    }

    @Throws(InsufficientFunds::class)
    fun withdraw(withdrawAmount: Double) {
        if (balance < withdrawAmount) {
            throw InsufficientFunds()
        }
        balance -= withdrawAmount;
    }


    override fun toString(): String {
        return "Balance: ${balance}"
    }

    override fun compareTo(other: BankAccount): Int {
        return compareValues(this.balance, other.balance)
    }
}

operator fun ClosedRange<BankAccount>.iterator() = object : Iterator<BankAccount> {
    var curr = start.balance
    override fun hasNext(): Boolean {
       return curr < endInclusive.balance;
    }

    override fun next(): BankAccount {
        val bb = BankAccount()
        bb.deposit(curr+1)
        curr += 1;
        return bb;
    }
}

class InsufficientFunds: Exception()