package pt.ul.fc.css.soccernow.config;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class UppercaseNamingStrategy extends CamelCaseToUnderscoresNamingStrategy {

    private Identifier adjustName(final Identifier name) {
        if (name == null) {
            return null;
        }
        final String adjustedName = name.getText()
                .toUpperCase();
        return new Identifier(adjustedName, true);
    }

    @Override
    public Identifier toPhysicalTableName(
            final Identifier logicalName, final JdbcEnvironment context) {
        return adjustName(super.toPhysicalTableName(logicalName, context));
    }

    @Override
    public Identifier toPhysicalColumnName(
            final Identifier logicalName, final JdbcEnvironment jdbcEnvironment) {
        return adjustName(super.toPhysicalColumnName(logicalName, jdbcEnvironment));
    }

    @Override
    public Identifier toPhysicalSequenceName(
            final Identifier logicalName, final JdbcEnvironment jdbcEnvironment) {
        return adjustName(super.toPhysicalSequenceName(logicalName, jdbcEnvironment));
    }

    @Override
    public Identifier toPhysicalCatalogName(
            final Identifier logicalName, final JdbcEnvironment jdbcEnvironment) {
        return adjustName(super.toPhysicalCatalogName(logicalName, jdbcEnvironment));
    }

    @Override
    public Identifier toPhysicalSchemaName(
            final Identifier logicalName, final JdbcEnvironment jdbcEnvironment) {
        return adjustName(super.toPhysicalSchemaName(logicalName, jdbcEnvironment));
    }
}
