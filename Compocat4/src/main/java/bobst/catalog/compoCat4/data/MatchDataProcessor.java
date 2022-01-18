package bobst.catalog.compoCat4.data;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import bobst.catalog.compoCat4.models.CatBom;

public class MatchDataProcessor implements ItemProcessor<MatchInput, CatBom> {

  private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);
  private static ArrayList<String> parents = new ArrayList<>();
  private static String idDoc = "BSA03802000066";

  @Override
  public CatBom process(final MatchInput matchInput) throws Exception {

    CatBom catBom = new CatBom();
    Integer level = Integer.parseInt(matchInput.getLevel());

    catBom.setIdDoc(MatchDataProcessor.idDoc);
    catBom.setItemToc(matchInput.getNumBobst());

    parents.add(level, matchInput.getNumBobst());
    catBom.setItemParent(parents.get(level-1));
    
    return catBom;
  }


}