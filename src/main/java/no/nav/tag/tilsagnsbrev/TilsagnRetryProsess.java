package no.nav.tag.tilsagnsbrev;

import lombok.extern.slf4j.Slf4j;
import no.nav.tag.tilsagnsbrev.dto.tilsagnsbrev.TilsagnUnderBehandling;
import no.nav.tag.tilsagnsbrev.feilet.FeiletTilsagnBehandler;
import no.nav.tag.tilsagnsbrev.integrasjon.PdfGenService;
import no.nav.tag.tilsagnsbrev.mapper.json.TilsagnJsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TilsagnRetryProsess {

    @Autowired
    Oppgaver oppgaver;

    @Autowired
    private PdfGenService pdfService;

    @Autowired
    FeiletTilsagnBehandler feiletTilsagnBehandler;

    @Autowired
    TilsagnJsonMapper tilsagnJsonMapper;


    //@Scheduled(cron = "${prosess.cron}")
    public void finnOgRekjoerFeiletTilsagn(){
        feiletTilsagnBehandler.hentAlleTilRekjoring()
                .forEach(feiletTilsagnsbrev -> {
                    rekjoerTilsagn(feiletTilsagnsbrev);
                });
    }

    private void rekjoerTilsagn(TilsagnUnderBehandling tilsagnUnderBehandling) {

        if (tilsagnUnderBehandling.skalMappesFraArenaMelding()) {
            oppgaver.arenaMeldingTilTilsagnData(tilsagnUnderBehandling);
        }

        tilsagnUnderBehandling.opprettTilsagn();
        final byte[] pdf = pdfService.tilsagnsbrevTilPdfBytes(tilsagnUnderBehandling);

        if (tilsagnUnderBehandling.skaljournalfoeres()) {
            oppgaver.journalfoerTilsagnsbrev(tilsagnUnderBehandling, pdf);
        }

        if (tilsagnUnderBehandling.skalTilAltinn()) {
            oppgaver.sendTilAltinn(tilsagnUnderBehandling, pdf);
        }
        tilsagnUnderBehandling.setBehandlet(true);
        feiletTilsagnBehandler.oppdater(tilsagnUnderBehandling);
    }
}
