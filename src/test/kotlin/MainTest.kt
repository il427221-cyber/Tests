import org.junit.Assert.*
import org.junit.Test

class MainTest {
    //tests for Mastercard
    @Test
    fun zeroCommission() {
        val card = "Mastercard"
        val previousTransfers = 26_000L
        val currentTransfer = 300L

        val result = calculateCommission(card,previousTransfers,currentTransfer)
        assertEquals("Комиссия составит 0 рублей.",result)

    }
    @Test
    fun currentTransferNotExceedsLimit() {
        val card = "Mastercard"
        val previousTransfers = 40_000L
        val currentTransfer = 40_000L

        val result = calculateCommission(card,previousTransfers,currentTransfer)
        assertEquals("Комиссия составит 50 рублей.",result)
    }
    @Test
    fun currentTransferExceedsLimit() {
        val card = "Mastercard"
        val previousTransfers = 80_000L
        val currentTransfer = 10_000L

        val result = calculateCommission(card,previousTransfers,currentTransfer)
        assertEquals("Комиссия составит 80 рублей.",result)
    }
    @Test
    fun minTransfer() {
        val card = "Mastercard"
        val previousTransfers = 500L
        val currentTransfer = 200L

        val result = calculateCommission(card,previousTransfers,currentTransfer)
        assertEquals("Минимальная сумма перевода с карт Mastercard или Maestro составляет 300 рублей ",result)
    }
    //tests for Visa
    @Test
    fun minCommissionWithVisa() {
        val card = "Visa"
        val previousTransfers = 500L
        val currentTransfer = 1000L

        val result = calculateCommission(card,previousTransfers,currentTransfer)
        assertEquals("Комиссия составит 35 рублей.",result)
    }
    @Test
    fun commissionWithVisa() {
        val card = "Visa"
        val previousTransfers = 80_000L
        val currentTransfer = 90_000L

        val result = calculateCommission(card,previousTransfers,currentTransfer)
        assertEquals("Комиссия составит 675 рублей.",result)
    }
    @Test
    fun vkPayExceedsMaxPerDay() {
        val card = "VK Pay"
        val previousTransfers = 10_000L
        val currentTransfer = 30_000L

        val result = calculateCommission(card,previousTransfers,currentTransfer)
        assertEquals("Операция заблокирована: превышен суточный лимит в 15000 руб.",result)
    }
    @Test
    fun vkPayExceedsMaxPerMonth() {
        val card = "VK Pay"
        val previousTransfers = 50_000L
        val currentTransfer = 10_000L

        val result = calculateCommission(card,previousTransfers,currentTransfer)
        assertEquals("Операция заблокирована: превышен месячный лимит в 40000 руб.",result)
    }
    @Test
    fun anotherCard() {
        val card = "ВТБ"
        val previousTransfers = 20_000L
        val currentTransfer = 5_000L

        val result = calculateCommission(card,previousTransfers,currentTransfer)
        assertEquals("Переводы с данной карты не поддерживаются",result)
    }
}