/*
 * This file is generated by jOOQ.
 */
package se4med.jooq.tables;


import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.jooq.types.UInteger;

import se4med.jooq.Indexes;
import se4med.jooq.Keys;
import se4med.jooq.Se4med;
import se4med.jooq.tables.records.PatientdocRecord;


/**
 * This table contains patients registered by doctors. This is necessary when 
 * doctors want to perform test treatments using their account.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Patientdoc extends TableImpl<PatientdocRecord> {

    private static final long serialVersionUID = 1697050144;

    /**
     * The reference instance of <code>se4med.patientdoc</code>
     */
    public static final Patientdoc PATIENTDOC = new Patientdoc();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PatientdocRecord> getRecordType() {
        return PatientdocRecord.class;
    }

    /**
     * The column <code>se4med.patientdoc.id</code>.
     */
    public final TableField<PatientdocRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>se4med.patientdoc.surname</code>.
     */
    public final TableField<PatientdocRecord, String> SURNAME = createField("surname", org.jooq.impl.SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>se4med.patientdoc.name</code>.
     */
    public final TableField<PatientdocRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>se4med.patientdoc.emaildoc</code>.
     */
    public final TableField<PatientdocRecord, String> EMAILDOC = createField("emaildoc", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>se4med.patientdoc.dateofbirth</code>.
     */
    public final TableField<PatientdocRecord, Date> DATEOFBIRTH = createField("dateofbirth", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * Create a <code>se4med.patientdoc</code> table reference
     */
    public Patientdoc() {
        this(DSL.name("patientdoc"), null);
    }

    /**
     * Create an aliased <code>se4med.patientdoc</code> table reference
     */
    public Patientdoc(String alias) {
        this(DSL.name(alias), PATIENTDOC);
    }

    /**
     * Create an aliased <code>se4med.patientdoc</code> table reference
     */
    public Patientdoc(Name alias) {
        this(alias, PATIENTDOC);
    }

    private Patientdoc(Name alias, Table<PatientdocRecord> aliased) {
        this(alias, aliased, null);
    }

    private Patientdoc(Name alias, Table<PatientdocRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("This table contains patients registered by doctors. This is necessary when doctors want to perform test treatments using their account."));
    }

    public <O extends Record> Patientdoc(Table<O> child, ForeignKey<O, PatientdocRecord> key) {
        super(child, key, PATIENTDOC);
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
        return Arrays.<Index>asList(Indexes.PATIENTDOC_FK_PATIENTDOC_1_IDX, Indexes.PATIENTDOC_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<PatientdocRecord, UInteger> getIdentity() {
        return Keys.IDENTITY_PATIENTDOC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<PatientdocRecord> getPrimaryKey() {
        return Keys.KEY_PATIENTDOC_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<PatientdocRecord>> getKeys() {
        return Arrays.<UniqueKey<PatientdocRecord>>asList(Keys.KEY_PATIENTDOC_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<PatientdocRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<PatientdocRecord, ?>>asList(Keys.FK_PATIENTDOC_1);
    }

    public Doctor doctor() {
        return new Doctor(this, Keys.FK_PATIENTDOC_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Patientdoc as(String alias) {
        return new Patientdoc(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Patientdoc as(Name alias) {
        return new Patientdoc(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Patientdoc rename(String name) {
        return new Patientdoc(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Patientdoc rename(Name name) {
        return new Patientdoc(name, null);
    }
}