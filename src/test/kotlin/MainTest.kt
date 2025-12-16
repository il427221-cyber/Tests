import org.junit.Assert.*
import org.junit.Test

class MainTest {
    @Test
    fun calculateCommissionWithMastercard() {
        val card = "Mastercard"
        val previousTransfers = 26_000L
        val currentTransfer = 300L

        val result = calculateCommission(card,previousTransfers,currentTransfer)
        assertEquals(0,result)

    }

}