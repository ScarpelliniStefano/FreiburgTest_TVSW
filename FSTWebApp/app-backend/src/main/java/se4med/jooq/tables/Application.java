/*
 * This file is generated by jOOQ.
 */
package se4med.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import se4med.jooq.Indexes;
import se4med.jooq.Keys;
import se4med.jooq.Se4med;
import se4med.jooq.tables.records.ApplicationRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Application extends TableImpl<ApplicationRecord> {

    private static final long serialVersionUID = 1401343267;

    /**
     * The reference instance of <code>se4med.application</code>
     */
    public static final Application APPLICATION = new Application();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ApplicationRecord> getRecordType() {
        return ApplicationRecord.class;
    }

    /**
     * The column <code>se4med.application.id</code>. questa tabella memorizza tutte le applicazioni. per ogni applicazione memorizza a quale progetto appartiene, il nome, una descrizione e se è una app web o no.

id applicazione, identifica univocamente l'applicazione
     */
    public final TableField<ApplicationRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.VARCHAR(10).nullable(false), this, "questa tabella memorizza tutte le applicazioni. per ogni applicazione memorizza a quale progetto appartiene, il nome, una descrizione e se è una app web o no.\n\nid applicazione, identifica univocamente l'applicazione");

    /**
     * The column <code>se4med.application.idproject</code>. fa riferimento all'id del progetto al quale l'applicazione appartiene -&gt; FK

se l'id della tabella project viene aggiornato, viene aggiornato anche idproject
se l'id della tabella project viene cancellato e c'è un record che contiene quell'id, non viene permessa la cancellazione del progetto
     */
    public final TableField<ApplicationRecord, Integer> IDPROJECT = createField("idproject", org.jooq.impl.SQLDataType.INTEGER, this, "fa riferimento all'id del progetto al quale l'applicazione appartiene -> FK\n\nse l'id della tabella project viene aggiornato, viene aggiornato anche idproject\nse l'id della tabella project viene cancellato e c'è un record che contiene quell'id, non viene permessa la cancellazione del progetto");

    /**
     * The column <code>se4med.application.name</code>.
     */
    public final TableField<ApplicationRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>se4med.application.description</code>.
     */
    public final TableField<ApplicationRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>se4med.application.webApp</code>. indica se l'applicazione è un'applicazione web oppure è un altro tipo di applicazione (es: per smartphone)
0 -&gt; NO WEB APP
1 -&gt; WEB APP
     */
    public final TableField<ApplicationRecord, Byte> WEBAPP = createField("webApp", org.jooq.impl.SQLDataType.TINYINT, this, "indica se l'applicazione è un'applicazione web oppure è un altro tipo di applicazione (es: per smartphone)\n0 -> NO WEB APP\n1 -> WEB APP");

    /**
     * Create a <code>se4med.application</code> table reference
     */
    public Application() {
        this(DSL.name("application"), null);
    }

    /**
     * Create an aliased <code>se4med.application</code> table reference
     */
    public Application(String alias) {
        this(DSL.name(alias), APPLICATION);
    }

    /**
     * Create an aliased <code>se4med.application</code> table reference
     */
    public Application(Name alias) {
        this(alias, APPLICATION);
    }

    private Application(Name alias, Table<ApplicationRecord> aliased) {
        this(alias, aliased, null);
    }

    private Application(Name alias, Table<ApplicationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Application(Table<O> child, ForeignKey<O, ApplicationRecord> key) {
        super(child, key, APPLICATION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Se4med.SE4MED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.APPLICATION_IDPROJECT_IDX, Indexes.APPLICATION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ApplicationRecord> getPrimaryKey() {
        return Keys.KEY_APPLICATION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ApplicationRecord>> getKeys() {
        return Arrays.<UniqueKey<ApplicationRecord>>asList(Keys.KEY_APPLICATION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ApplicationRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ApplicationRecord, ?>>asList(Keys.IDPROJECT);
    }

    public Project project() {
        return new Project(this, Keys.IDPROJECT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Application as(String alias) {
        return new Application(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Application as(Name alias) {
        return new Application(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Application rename(String name) {
        return new Application(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Application rename(Name name) {
        return new Application(name, null);
    }
}