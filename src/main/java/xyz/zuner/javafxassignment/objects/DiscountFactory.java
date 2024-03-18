        return discountMap.get(code);
    }

    private static void printAllDiscounts() {
        discountMap.forEach((code, discount) -> System.out.println(discount.toString()));
    }
}
