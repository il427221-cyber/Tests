fun main() {
    val result = calculateCommission("Mastercard", 26_000, 300)
    println(result)
}

// функция вычисления комиссии с параметрами: тип карты, предыдущие переводы, текущий перевод
fun calculateCommission(card: String, previousTransfers: Long, currentTransfer: Long): String {
    var maxPerDay = 150_000
    var maxPerMonth = 600_000
    var commission = 0.0
    // если текущий перевод больше суточного лимита, блокируем операцию
    if (currentTransfer > maxPerDay) {
        return ("Операция заблокирована: превышен суточный лимит в $maxPerDay руб.")
    } // если сумма предыдущих переводов и текущего перевода больше месячного лимита, также блокируем операцию
    if (previousTransfers + currentTransfer > maxPerMonth) {
        return ("Операция заблокирована: превышен месячный лимит в $maxPerMonth руб.")
    }

    when (card) {
        "VK Pay" -> {
            maxPerDay = 15_000
            maxPerMonth = 40_000
            commission = 0.0
            if (currentTransfer > maxPerDay) {
                return ("Операция заблокирована: превышен суточный лимит в $maxPerDay руб.")
            } // если сумма предыдущих переводов и текущего перевода больше месячного лимита, также блокируем операцию
            if (previousTransfers + currentTransfer > maxPerMonth) {
                return ("Операция заблокирована: превышен месячный лимит в $maxPerMonth руб.")
            }
        }

        "Visa", "Мир" -> {
            commission = currentTransfer * 0.0075
            if (commission < 35.0) {
                commission = 35.0
            }
        }

        "Mastercard", "Maestro" -> {
            // для карты Mastercard прописывваем месячный лимит в 75 000 рублей, за превышение которого берется комиссия
            var monthlyLimit = 75_000L
            // вычисляем сумму оставшегося у нас лимита
            val transferWithoutCommission = monthlyLimit - previousTransfers
            if (currentTransfer < 300) {
                return "Минимальная сумма перевода с карт Mastercard или Maestro составляет 300 рублей "
            }
            when {
                // если текущий перевод не превышает этой суммы, комиссия не взымается
                transferWithoutCommission >= currentTransfer && currentTransfer >= 300 -> {
                    commission = 0.0
                }
                // если сумма больше нуля, то вычисляем остаток, с которого должна взыматься комиссия, и рассчитываем эту комиссию
                transferWithoutCommission > 0 -> {
                    val amountWithCommission = currentTransfer - transferWithoutCommission
                    commission = amountWithCommission * 0.006 + 20
                }
                // для оставшихся случаев вычисляем комиссию с суммы текущего перевода
                else -> {
                    commission = currentTransfer * 0.006 + 20
                }
            }
        } // добавил условие обработки другой карты
        else -> {
            return "Переводы с данной карты не поддерживаются"
        }

    } // округление комиссии до ближайшего целого числа
    val roundedAmount = kotlin.math.round(commission).toInt()
    return ("Комиссия составит $roundedAmount рублей.")
}