package com.vireosci.sky.common.data;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/// 性别
public enum Gender
{
    /// 男性
    MAIL,
    /// 女性
    FEMALE;

    public Character abbreviation() { return name().charAt(0); }

    @Converter(autoApply = true)
    public static class GenderConverter implements AttributeConverter<Gender, Character>
    {
        @Override
        public Character convertToDatabaseColumn(Gender attribute)
        {
            if (attribute == null) return null;
            return attribute.abbreviation();
        }

        @Override
        public Gender convertToEntityAttribute(Character dbData)
        {
            if (dbData == null) return null;
            Gender gender = null;
            for (var g : Gender.values())
                if (g.abbreviation().equals(dbData))
                    gender = g;
            return gender;
        }
    }
}
