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
import se4med.jooq.tables.records.PatientRecord;


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
public class Patient extends TableImpl<PatientRecord> {

    private static final long serialVersionUID = 197686527;

    /**
     * The reference instance of <code>se4med.patient</code>
     */
    public static final Patient PATIENT = new Patient();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PatientRecord> getRecordType() {
        return PatientRecord.class;
    }

    /**
     * The column <code>se4med.patient.username</code>. L'utente si registra fornendo UserName, Surname, Name, DataOfBirth, EmailUser e Password.
Nel caso di prima registrazione (l'email non è mai stata registrata), viene anche inserita un'istanza nella tabella EMAIL. Se chi fa il test non è il proprietario dell'email devono essere forniti anche nome e cognome 
del proprietario dell'email e vengono salvati nella tabelle Email. Nel caso in cui il proprietario dell'email corrisponda all'utente registrato, vengono riportati nome e cognome nella tabella Email. Questo perché se 
gli utenti sono bambini e utilizzano l'email del genitore si vuole memorizzare nome e cognome del genitore.
Una volta creato un utente con email X, per poter creare altri utenti è necessario prima fare il login e successivamente permettere l'inserimento di altri utenti.
Il login avviene nel seguente modo:
Inserimento email e password
Se è associato più di un utente viene mostrato
     */
    public final TableField<PatientRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "L'utente si registra fornendo UserName, Surname, Name, DataOfBirth, EmailUser e Password.\nNel caso di prima registrazione (l'email non è mai stata registrata), viene anche inserita un'istanza nella tabella EMAIL. Se chi fa il test non è il proprietario dell'email devono essere forniti anche nome e cognome \ndel proprietario dell'email e vengono salvati nella tabelle Email. Nel caso in cui il proprietario dell'email corrisponda all'utente registrato, vengono riportati nome e cognome nella tabella Email. Questo perché se \ngli utenti sono bambini e utilizzano l'email del genitore si vuole memorizzare nome e cognome del genitore.\nUna volta creato un utente con email X, per poter creare altri utenti è necessario prima fare il login e successivamente permettere l'inserimento di altri utenti.\nIl login avviene nel seguente modo:\nInserimento email e password\nSe è associato più di un utente viene mostrato");

    /**
     * The column <code>se4med.patient.surname</code>.
     */
    public final TableField<PatientRecord, String> SURNAME = createField("surname", org.jooq.impl.SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>se4med.patient.name</code>.
     */
    public final TableField<PatientRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>se4med.patient.dateofbirth</code>.
     */
    public final TableField<PatientRecord, Date> DATEOFBIRTH = createField("dateofbirth", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>se4med.patient.emailpatient</code>. fa riferiemento all'email con cui l'utente è registrato

se l'email della tabella user viene cancellata/modificata, si cancellano/modificano tutti i record che contengono quel valore
     */
    public final TableField<PatientRecord, String> EMAILPATIENT = createField("emailpatient", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "fa riferiemento all'email con cui l'utente è registrato\n\nse l'email della tabella user viene cancellata/modificata, si cancellano/modificano tutti i record che contengono quel valore");

    /**
     * The column <code>se4med.patient.settings</code>.
     */
    public final TableField<PatientRecord, String> SETTINGS = createField("settings", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>se4med.patient</code> table reference
     */
    public Patient() {
        this(DSL.name("patient"), null);
    }

    /**
     * Create an aliased <code>se4med.patient</code> table reference
     */
    public Patient(String alias) {
        this(DSL.name(alias), PATIENT);
    }

    /**
     * Create an aliased <code>se4med.patient</code> table reference
     */
    public Patient(Name alias) {
        this(alias, PATIENT);
    }

    private Patient(Name alias, Table<PatientRecord> aliased) {
        this(alias, aliased, null);
    }

    private Patient(Name alias, Table<PatientRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Patient(Table<O> child, ForeignKey<O, PatientRecord> key) {
        super(child, key, PATIENT);
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
        return Arrays.<Index>asList(Indexes.PATIENT_EMAILUSER, Indexes.PATIENT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<PatientRecord> getPrimaryKey() {
        return Keys.KEY_PATIENT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<PatientRecord>> getKeys() {
        return Arrays.<UniqueKey<PatientRecord>>asList(Keys.KEY_PATIENT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<PatientRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<PatientRecord, ?>>asList(Keys.EMAILPATIENT);
    }

    public User user() {
        return new User(this, Keys.EMAILPATIENT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Patient as(String alias) {
        return new Patient(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Patient as(Name alias) {
        return new Patient(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Patient rename(String name) {
        return new Patient(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Patient rename(Name name) {
        return new Patient(name, null);
    }
}
