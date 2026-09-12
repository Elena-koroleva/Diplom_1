package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BurgerParamTest {

    private Burger burger;
    @Mock
    private Bun mockBun;
    @Mock
    private Ingredient mockSauce;
    @Mock
    private Ingredient mockFilling;

    private final float bunPrice;
    private final float saucePrice;
    private final float fillingPrice;
    private final float expectedPrice;
    private final int ingredientsCount; // Сколько ингредиентов нужно добавить в этом тесте

    public BurgerParamTest(float bunPrice, float ingredient1Price, float ingredient2Price, int ingredientsCount, float expectedPrice) {
        this.bunPrice = bunPrice;
        this.saucePrice = ingredient1Price;
        this.fillingPrice = ingredient2Price;
        this.ingredientsCount = ingredientsCount;
        this.expectedPrice = expectedPrice;
    }

    @Parameterized.Parameters(name = "Сценарий {index}: булочка {0} руб, добавок {3} шт -> Итого: {4} руб")
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                { 100.0f, 0.0f,   0.0f,   0, 200.0f }, // Тест 1: Только булочка (100 * 2 = 200)
                { 100.0f, 50.0f,  0.0f,   1, 250.0f }, // Тест 2: Булочка + 1 добавка (100 * 2 + 50 = 250)
                { 150.0f, 70.0f,  80.0f,  2, 450.0f }  // Тест 3: Булочка + 2 добавки (150 * 2 + 70 + 80 = 450)
        });
    }

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();
    }

    @Test
    public void getPriceDifferentInputsReturnsCorrectTotalPrice() {
        //Обучаем моки возвращать цены
        Mockito.when(mockBun.getPrice()).thenReturn(bunPrice);
        Mockito.when(mockSauce.getPrice()).thenReturn(saucePrice);
        Mockito.when(mockFilling.getPrice()).thenReturn(fillingPrice);

        //Собираем бургер
        burger.setBuns(mockBun);
        if (ingredientsCount >= 1) {
            burger.addIngredient(mockSauce);
        }
        if (ingredientsCount == 2) {
            burger.addIngredient(mockFilling);
        }

        //Считаем цену и проверяем результат
        float actualPrice = burger.getPrice();
        assertEquals("Итоговая стоимость бургера рассчитана неверно",
                expectedPrice, actualPrice, 0.0f);
    }
}
