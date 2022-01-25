package com.billennium.vaccinationproject.google.sheets;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;

@RestController
@RequestMapping("/google/sheets")
@AllArgsConstructor
public class GoogleSheetsControler {

    private GoogleSheetsService googleSheetsService;
    private final String spreadsheetId = "1ptbYP5N3gd3XYXz-WV78ET2hdoT65tjqCST_v76xkF8";

    //from : "A2"
    // valueBody: {"values": [["Expenses January"],["books","30"],["pens","10"],["Expenses February"],["clothes","20"],["shoes","5"]]}
    @GetMapping("/addDataTest")
    public ResponseEntity<?> gSheetsTest (@RequestBody ValueRange valueBody, @RequestParam String from) throws GeneralSecurityException, IOException {
        Sheets sheet = googleSheetsService.getSheet();
        googleSheetsService.updateData(sheet, valueBody, from);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //range : "Sheet1!A2:B7"
    @GetMapping("/getDataTest")
    public ResponseEntity<Collection<Object>> getAllData(@RequestParam String range) throws GeneralSecurityException, IOException{
        return new ResponseEntity<>(googleSheetsService.getSheetData(range).values(),HttpStatus.OK);
    }
}
