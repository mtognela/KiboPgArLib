package com.kibo.pgar.lib.Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.kibo.pgar.lib.Formats.AnsiColors;
import com.kibo.pgar.lib.Formats.AnsiWeights;
import com.kibo.pgar.lib.Strings.PrettyStrings;

/**
 * TypeToken class to capture and preserve generic type information
 */
public class TypeToken<T> {
    private static final String TYPE_TOKEN_MUST_BE_PARAMETERIZED = PrettyStrings.prettify(AnsiColors.RED,
            AnsiWeights.BOLD,
            null, "TypeToken must be parameterized");
    private final Type type;
    private final int hashCode;

    public TypeToken() {
        Type superclass = getClass().getGenericSuperclass();

        if (superclass instanceof ParameterizedType) {
            this.type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
        } else {
            throw new IllegalArgumentException(TYPE_TOKEN_MUST_BE_PARAMETERIZED);
        }

        this.hashCode = type.hashCode();
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (!(obj instanceof TypeToken))
            return false;
        TypeToken<?> other = (TypeToken<?>) obj;
        return type.equals(other.type);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "TypeToken{" + type + "}";
    }
}