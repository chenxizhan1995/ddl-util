
-- 表定义明细
select S.TABLE_SCHEMA,T.TABLE_COMMENT, S.TABLE_NAME , S.COLUMN_NAME, S.COLUMN_TYPE, S.COLUMN_COMMENT
from information_schema.COLUMNS S
         join information_schema.TABLES T using(table_schema, table_name)
         join (
    select 1 as seq, 'ca_image' as table_name union
    select 2 as seq, 'ca_label_head' as table_name union
    select 3 as seq, 'ca_label_item' as table_name union
    select 4 as seq, 'ca_ingredients_category' as table_name union
    select 5 as seq, 'ca_ingredients_item' as table_name union
    select 6 as seq, 'ca_ingredients_supplier' as table_name union
    select 7 as seq, 'ca_measure_unit' as table_name union
    select 8 as seq, 'ca_inventory' as table_name union
    select 9 as seq, 'ca_inbound' as table_name union
    select 10 as seq, 'ca_outbound' as table_name union
    select 11 as seq, 'ca_product_sample' as table_name union
    select 12 as seq, 'ca_product_label' as table_name union
    select 13 as seq, 'ca_product_review' as table_name union
    select 14 as seq, 'ca_product_review_image_rel' as table_name union
    select 15 as seq, 'ca_product_review_label' as table_name union
    select 16 as seq, 'ca_review_weight' as table_name union
    select 17 as seq, 'ca_cost_statistics' as table_name union
    select 18 as seq, 'ca_product' as table_name
) U on U.table_name = S.table_name
where TABLE_SCHEMA = 'ai_building'
order by U.seq, COLUMN_KEY
;

# -- 表清单
# select distinct  S.TABLE_SCHEMA, S.TABLE_NAME, T.TABLE_COMMENT
# from information_schema.COLUMNS S
#          join information_schema.TABLES T using(table_schema, table_name)
#          join lhc_tb U on U.table_name = S.table_name
# where TABLE_SCHEMA = 'ai_building'
# order by U.id, COLUMN_KEY desc
# ;