package no.nav.tag.tilsagnsbrev.dto.tilsagnsbrev;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class  Tilsagn {

    private String administrasjonKode;
    
    private String antallDeltakere;
    
    private String antallTimeverk;
    
    private Person beslutter;
    
    private Deltaker deltaker;
    
    private String kommentar;
    
    private NavEnhet navEnhet;
    
    private Periode periode;
    
    private Person saksbehandler;

    private LocalDate tilsagnDato;

    private LocalDate refusjonfristDato;

    private TilsagnNummer tilsagnNummer;
    
    private List<Tilskudd> tilskuddListe;
    
    private TiltakArrangor tiltakArrangor;
    
    private String tiltakKode;
    
    private String tiltakNavn;
    
    private String totaltTilskuddbelop;

    private String valutaKode;

    public boolean erGruppeTilsagn(){
        return this.deltaker == null;
    }
}
